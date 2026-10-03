package com.kkoreng.jda.core.command.registrar;

import com.kkoreng.jda.core.command.SlashCommand;
import com.kkoreng.jda.core.command.registry.CommandRegistry;
import com.kkoreng.jda.logging.Loggers;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.List;

public final class SlashCommandRegistrar {

    private final JDA jda;
    private final CommandRegistry registry;
    private final long guildId;

    public SlashCommandRegistrar(JDA jda, CommandRegistry registry, long guildId) {
        this.jda = jda;
        this.registry = registry;
        this.guildId = guildId;
    }

    public void registerCommands() {
        List<SlashCommandData> commands = registry.getSlashCommands().stream()
                .map(SlashCommand::data)
                .toList();

        if (guildId == 0) {
            jda.updateCommands().addCommands(commands).queue(
                    registered -> Loggers.COMMAND.info("Registered {} global slash commands", registered.size()),
                    error -> Loggers.COMMAND.error("Failed to register global slash commands", error)
            );
            return;
        }

        Guild guild = jda.getGuildById(guildId);
        if (guild == null) {
            throw new IllegalStateException("COMMAND_GUILD_ID guild not found: " + guildId);
        }

        guild.updateCommands().addCommands(commands).queue(
                registered -> Loggers.COMMAND.info("Registered {} slash commands to guild {}", registered.size(), guild.getName()),
                error -> Loggers.COMMAND.error("Failed to register slash commands to guild {}", guild.getName(), error)
        );
    }
}