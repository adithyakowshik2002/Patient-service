package com.improveid.HMS.Patient_Service.Exception;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String message){
        super(message);
    }
}
