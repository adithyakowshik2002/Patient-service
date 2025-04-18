package com.improveid.HMS.Patient_Service.Exception;

import lombok.*;

import java.util.Map;

@Builder
@Setter
@Getter
@Data
@AllArgsConstructor
public class ErrorResponse {

    private int status;
    private String message;
    private Map<String,String> fieldErrors;


}
