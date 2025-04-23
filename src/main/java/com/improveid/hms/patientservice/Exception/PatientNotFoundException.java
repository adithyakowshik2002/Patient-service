package com.improveid.hms.patientservice.Exception;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String message){
        super(message);
    }
}
