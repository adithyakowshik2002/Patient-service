package com.improveid.HMS.Patient_Service.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private int status;
    private String message;
    private Map<String,String> fieldErrors;


}
