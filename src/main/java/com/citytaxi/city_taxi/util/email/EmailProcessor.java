package com.citytaxi.city_taxi.util.email;

import com.citytaxi.city_taxi.models.dtos.email.SendRegistrationEmailRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component
@Log4j2
@RequiredArgsConstructor
public class EmailProcessor {
    private final BlockingQueue<SendRegistrationEmailRequest> emailQueue;
    private final EmailService emailService;

    @Scheduled(fixedDelay = 2000)
    public void processEmailQueue() {
        while (!emailQueue.isEmpty()) {
            log.debug("Processing email queue...");
            try {
                SendRegistrationEmailRequest emailRequest = emailQueue.take();
                emailService.sendEmail(emailRequest);
                log.info("Email sent to {}", emailRequest.getTo());
            } catch (InterruptedException | MessagingException e) {
                log.error("Failed to send email", e);
            }
        }
    }
}