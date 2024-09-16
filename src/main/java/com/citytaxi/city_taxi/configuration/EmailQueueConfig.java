package com.citytaxi.city_taxi.configuration;

import com.citytaxi.city_taxi.models.dtos.email.SendRegistrationEmailRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class EmailQueueConfig {
    @Bean
    public BlockingQueue<SendRegistrationEmailRequest> emailQueue() {
        return new LinkedBlockingQueue<>();
    }
}
