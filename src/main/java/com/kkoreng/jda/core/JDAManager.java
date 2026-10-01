package com.kkoreng.jda.core;

import com.kkoreng.jda.config.ConfigProvider;
import com.kkoreng.jda.logging.Loggers;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public final class JDAManager {

    private final ConfigProvider config;
    private JDA jda;

    public JDAManager(ConfigProvider config) {
        this.config = config;
    }

    public void start() {
        final String TOKEN = System.getenv("DISCORD_TOKEN");

        try {
            Loggers.BOT.info("Starting JDA...");

            JDABuilder builder = JDABuilder.createDefault(TOKEN);

            this.jda = builder.build();
            this.jda.awaitReady();

            Loggers.BOT.info(
                    "Logged in as {}",
                    jda.getSelfUser().getName()
            );

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Loggers.BOT.error("JDA startup interrupted", e);
            throw new RuntimeException(e);

        } catch (Exception e) {
            Loggers.BOT.error("Failed to start JDA", e);
            throw new RuntimeException(e);
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