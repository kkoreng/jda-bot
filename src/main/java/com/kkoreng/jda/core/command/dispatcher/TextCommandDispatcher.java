package com.kkoreng.jda.core.command.dispatcher;

import com.kkoreng.jda.core.command.TextCommand;
import com.kkoreng.jda.core.command.registry.CommandRegistry;
import com.kkoreng.jda.logging.Loggers;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public final class TextCommandDispatcher extends ListenerAdapter {

    private final CommandRegistry registry;
    private final String prefix;

    public TextCommandDispatcher(CommandRegistry registry, String prefix) {
        this.registry = registry;
        this.prefix = prefix;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        String content = event.getMessage().getContentRaw();
        if (!content.startsWith(prefix)) {
            return;
        }

        String label = content.substring(prefix.length()).trim().split("\\s+", 2)[0];
        if (label.isEmpty()) {
            return;
        }

        TextCommand command = registry.getTextCommand(label);
        if (command == null) {
            return;
        }

        try {
            command.execute(event);
        } catch (Exception e) {
            Loggers.COMMAND.error("Failed to execute {}{}", prefix, label, e);
        }
    }

}
