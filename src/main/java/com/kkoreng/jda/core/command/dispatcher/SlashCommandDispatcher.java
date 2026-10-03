package com.kkoreng.jda.core.command.dispatcher;

import com.kkoreng.jda.core.command.SlashCommand;
import com.kkoreng.jda.core.command.registry.CommandRegistry;
import com.kkoreng.jda.logging.Loggers;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public final class SlashCommandDispatcher extends ListenerAdapter {

    private final CommandRegistry registry;

    public SlashCommandDispatcher(CommandRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        SlashCommand command = registry.getSlashCommand(event.getName());
        if (command == null) {
            Loggers.COMMAND.warn("Unknown slash command: /{}", event.getFullCommandName());
            return;
        }

        try {
            command.execute(event);
        } catch (Exception e) {
            Loggers.COMMAND.error("Failed to execute /{}", event.getFullCommandName(), e);
            if (!event.isAcknowledged()) {
                event.reply("명령어 실행 중 오류가 발생했습니다.").setEphemeral(true).queue();
            }
        }
    }

}