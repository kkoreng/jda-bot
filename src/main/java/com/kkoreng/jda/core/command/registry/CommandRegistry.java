package com.kkoreng.jda.core.command.registry;

import com.kkoreng.jda.core.command.SlashCommand;
import com.kkoreng.jda.core.command.TextCommand;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class CommandRegistry {

    private final Map<String, SlashCommand> slashCommands;
    private final Map<String, TextCommand> textCommands;

    public CommandRegistry() {
        this.slashCommands = new HashMap<>();
        this.textCommands = new HashMap<>();
    }

    public void putSlashCommand(SlashCommand command) {
        put(slashCommands, command.data().getName(), command);
    }

    public void putTextCommand(TextCommand command) {
        put(textCommands, command.name(), command);
        for (String alias : command.aliases()) {
            put(textCommands, alias, command);
        }
    }

    public SlashCommand getSlashCommand(String name) {
        return slashCommands.get(name.toLowerCase(Locale.ROOT));
    }

    public TextCommand getTextCommand(String name) {
        return textCommands.get(name.toLowerCase(Locale.ROOT));
    }

    public Collection<SlashCommand> getSlashCommands() {
        return Collections.unmodifiableCollection(slashCommands.values());
    }

    public Collection<TextCommand> getTextCommands() {
        return Collections.unmodifiableCollection(textCommands.values());
    }

    private static <T> void put(Map<String, T> commands, String name, T command) {
        if (commands.putIfAbsent(name.toLowerCase(Locale.ROOT), command) != null) {
            throw new IllegalStateException("Duplicate command name: " + name);
        }
    }

}
