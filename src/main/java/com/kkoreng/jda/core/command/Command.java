package com.kkoreng.jda.core.command;

public interface Command <E> {
    void execute(E event);
}
