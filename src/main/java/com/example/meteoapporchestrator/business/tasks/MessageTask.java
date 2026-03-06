package com.example.meteoapporchestrator.business.tasks;

import com.example.meteoapporchestrator.business.model.CollectConfigurationDTO;

public class MessageTask implements Runnable {
    private CollectConfigurationDTO dto;

    public MessageTask(CollectConfigurationDTO dto) {
        this.dto = dto;
    }

    public CollectConfigurationDTO getDto() {
        return dto;
    }

    public void setDto(CollectConfigurationDTO dto) {
        this.dto = dto;
    }

    public void run() {
        System.out.println("Running " + dto.name());
    }
}
