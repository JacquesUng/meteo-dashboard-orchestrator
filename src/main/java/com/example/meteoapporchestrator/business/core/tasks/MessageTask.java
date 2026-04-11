package com.example.meteoapporchestrator.business.core.tasks;

import com.example.meteoapporchestrator.business.model.CollectConfiguration;

public class MessageTask implements Runnable {
    private CollectConfiguration config;

    public MessageTask(CollectConfiguration config) {
        this.config = config;
    }

    public CollectConfiguration getConfig() {
        return config;
    }

    public void setConfig(CollectConfiguration config) {
        this.config = config;
    }

    @Override
    public void run() {
        System.out.println("Running " + config.name());
    }
}
