package com.kkoreng.jda.core;

import com.kkoreng.jda.config.ConfigProvider;
import com.kkoreng.jda.core.command.dispatcher.SlashCommandDispatcher;
import com.kkoreng.jda.core.command.dispatcher.TextCommandDispatcher;
import com.kkoreng.jda.core.command.registrar.SlashCommandRegistrar;
import com.kkoreng.jda.core.command.registry.CommandRegistry;
import com.kkoreng.jda.logging.Loggers;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public final class JDAManager {

    private static final String DEFAULT_PREFIX = "!";

    private final ConfigProvider config;
    private final String basePackage;
    private JDA jda;

    public JDAManager(ConfigProvider config, String basePackage) {
        this.config = config;
        this.basePackage = basePackage;
    }

    public void start() {
        try {
            Loggers.BOT.info("Starting JDA...");

            CommandRegistry commandRegistry = loadCommands();
            this.jda = login(commandRegistry);
            registerSlashCommands(commandRegistry);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Loggers.BOT.error("JDA startup interrupted", e);
            throw new RuntimeException(e);

        } catch (Exception e) {
            Loggers.BOT.error("Failed to start JDA", e);
            throw new RuntimeException(e);
        }
    }

    private CommandRegistry loadCommands() {
        CommandRegistry commandRegistry = new ComponentLoader().loadCommands(basePackage);
        Loggers.COMMAND.info(
                "Loaded {} slash commands, {} text command names",
                commandRegistry.getSlashCommands().size(),
                commandRegistry.getTextCommands().size()
        );
        return commandRegistry;
    }


    private JDA login(CommandRegistry commandRegistry) throws InterruptedException {
        final
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String token = dotenv.get("TOKEN");

        JDABuilder builder = JDABuilder.createDefault(token)
                .addEventListeners(new SlashCommandDispatcher(commandRegistry));

        if (!commandRegistry.getTextCommands().isEmpty()) {
            builder.enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .addEventListeners(new TextCommandDispatcher(commandRegistry, prefix()));
        }

        JDA jda = builder.build();
        jda.awaitReady();

        Loggers.BOT.info("Logged in as {}", jda.getSelfUser().getName());
        return jda;
    }

    private void registerSlashCommands(CommandRegistry commandRegistry) {
        new SlashCommandRegistrar(jda, commandRegistry, guildId()).registerCommands();
    }

    private String prefix() {
        String prefix = config.getString("PREFIX");
        return prefix == null || prefix.isBlank() ? DEFAULT_PREFIX : prefix;
    }


    private long guildId() {
        String value = config.getString("GUILD_ID");
        if (value == null || value.isBlank()) {
            return 0L;
        }

        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalStateException("GUILD_ID must be a number: " + value, e);
        }
    }

    public void stop() {
        if (jda == null) {
            return;
        }

        Loggers.BOT.info("Shutting down JDA...");
        jda.shutdown();
        Loggers.BOT.info("JDA has been shut down.");
    }

}
