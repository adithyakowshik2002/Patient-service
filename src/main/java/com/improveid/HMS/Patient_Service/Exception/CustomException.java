package com.improveid.HMS.Patient_Service.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException{

    private final int status;
    private final String message;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status.value();
        this.message = message;

    }
}
