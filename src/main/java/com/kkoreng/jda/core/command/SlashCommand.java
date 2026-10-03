package com.kkoreng.jda.core.command;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public interface SlashCommand extends Command<SlashCommandInteractionEvent> {
    SlashCommandData data();
}