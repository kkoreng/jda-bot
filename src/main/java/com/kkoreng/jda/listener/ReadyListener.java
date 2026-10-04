package com.kkoreng.jda.listener;

import com.kkoreng.jda.core.annotation.RegisterListener;
import com.kkoreng.jda.logging.Loggers;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@RegisterListener
public class ReadyListener extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        Loggers.EVENT.info("{} is ready 리스너 테스트",
                event.getJDA().getSelfUser().getName());
    }
}