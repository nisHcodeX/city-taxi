package com.citytaxi.city_taxi.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerException extends ApiException {
    public InternalServerException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
