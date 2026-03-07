package com.example.meteoapporchestrator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class TaskSchedulerConfig {
    @Bean
    public TaskScheduler taskScheduler() {
        // On laisse la taille du pool de thread à 1 (valeur par défaut)
        // On n'attribue pas de préfixe aux noms des threads
        return new ThreadPoolTaskScheduler();
    }
}
