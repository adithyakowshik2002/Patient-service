package com.improveid.hms.patientservice.Exception;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String message){

        super(message);
    }
}
