package com.kkoreng.jda;

import com.kkoreng.jda.config.ConfigProvider;
import com.kkoreng.jda.core.JDAManager;

public class JDABot {

    public static void main(String[] args) {

        ConfigProvider config = new ConfigProvider();

        JDAManager jdaManager = new JDAManager(config, JDABot.class.getPackageName());
        jdaManager.start();


    }

}

