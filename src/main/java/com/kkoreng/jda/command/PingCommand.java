package com.kkoreng.jda.command;

import com.kkoreng.jda.core.annotation.RegisterCommand;
import com.kkoreng.jda.core.command.SlashCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

@RegisterCommand
public class PingCommand implements SlashCommand {

    @Override
    public SlashCommandData data() {
        return Commands.slash("ping", "봇의 응답 속도를 확인합니다.");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        long gatewayPing = event.getJDA().getGatewayPing();
        long start = System.currentTimeMillis();

        event.reply("측정 중...").setEphemeral(true).queue(hook -> {
            long restPing = System.currentTimeMillis() - start;
            hook.editOriginal("**Pong!** \nGateway: " + gatewayPing + "ms\nREST: " + restPing + "ms").queue();
        });
    }
}