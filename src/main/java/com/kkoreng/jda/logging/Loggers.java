package com.kkoreng.jda.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Loggers {

    public static final Logger BOT = LoggerFactory.getLogger("BOT");

    public static final Logger DATABASE = LoggerFactory.getLogger("DATABASE");

    public static final Logger COMMAND = LoggerFactory.getLogger("COMMAND");

    public static final Logger EVENT = LoggerFactory.getLogger("EVENT");

    private Loggers() {}
}