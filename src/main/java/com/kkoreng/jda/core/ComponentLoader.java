package com.kkoreng.jda.core;

import com.kkoreng.jda.core.annotation.RegisterCommand;
import com.kkoreng.jda.core.command.SlashCommand;
import com.kkoreng.jda.core.command.TextCommand;
import com.kkoreng.jda.core.command.registry.CommandRegistry;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

import java.lang.reflect.Modifier;

/**
 * Reflection을 통한 Annotation 구분 - ClassGraph 사용
 * - RegisterListener
 * - RegisterCommand
 *    - SlashCommand
 *    - TextCommand
 */
public final class ComponentLoader {

    public CommandRegistry loadCommands(String basePackage) {
        CommandRegistry registry = new CommandRegistry();
//        List<EventListener> listeners = new ArrayList<>();

        try (ScanResult scan = new ClassGraph()
                .enableAnnotationInfo()
                .acceptPackages(basePackage)
                .scan()) {

            ClassInfoList commandClasses = scan.getClassesWithAnnotation(RegisterCommand.class);

            for (ClassInfo info : commandClasses) {
                if (!info.implementsInterface(SlashCommand.class) && !info.implementsInterface(TextCommand.class)) {
                    throw new IllegalStateException(info.getName()
                            + " has @RegisterCommand but implements neither SlashCommand nor TextCommand");
                }
            }

            for (Class< SlashCommand> type : commandClasses
                    .filter(info -> info.implementsInterface(SlashCommand.class))
                    .loadClasses(SlashCommand.class)) {

                registry.putSlashCommand(instantiate(type));
            }

            for (Class<TextCommand> type : commandClasses
                    .filter(info -> info.implementsInterface(TextCommand.class))
                    .loadClasses(TextCommand.class)) {

                registry.putTextCommand(instantiate(type));
            }


/*
            for (Class<?> type : scan.getClassesWithAnnotation(Listener.class).loadClasses()) {
                if (!(instantiate(type) instanceof EventListener listener)) {
                    throw new IllegalStateException(type.getName() + " must implement EventListener");
                }
                listeners.add(listener);
            }*/
        }

        return registry;
    }

    private static <T> T instantiate(Class<T> type) {
        if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
            throw new IllegalStateException(type.getName() + " must be a concrete class");
        }

        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(type.getName() + " needs a no-args constructor", e);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to instantiate " + type.getName(), e);
        }
    }

}
