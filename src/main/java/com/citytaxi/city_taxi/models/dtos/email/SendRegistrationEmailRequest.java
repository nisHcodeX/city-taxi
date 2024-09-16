package com.citytaxi.city_taxi.models.dtos.email;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendRegistrationEmailRequest {
    private String to;
    private String subject;
    private String username;
    private String password;
}
