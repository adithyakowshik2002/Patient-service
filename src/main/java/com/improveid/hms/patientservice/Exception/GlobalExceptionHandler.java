package com.improveid.hms.patientservice.Exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException ex){

        Map<String,String> errors = ex.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(
                FieldError::getField,
                fieldError -> Optional.ofNullable(fieldError.getDefaultMessage()).orElse("Invalid value")
        ));

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), "validation failed",errors
        );

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException ex) {

        return buildErrorResponse("Custom exceptions",ex.getMessage(), HttpStatus.valueOf(ex.getStatus()));
    }

    public ResponseEntity<Object> handleRunTimeException(RuntimeException ex){
        return buildErrorResponse("Internal Server Error",ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDatabaseException(DataAccessException ex){
        return buildErrorResponse("Database Exception",ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException ex){
        return buildErrorResponse("Null Pointer Exception",ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex){
        return buildErrorResponse("Method not Allowed",ex.getMessage(),HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<Object> hadlePatientNotFound(PatientNotFoundException ex){
        return buildErrorResponse("patient not found",ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex){
        return buildErrorResponse("Not Found", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(HttpMessageNotReadableException ex){
        Throwable rootCause = ex.getMostSpecificCause();

        if(rootCause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException && rootCause.getMessage().contains("BloodGroup")){

            String message = "Invalid blood group value. Accepted values are: B_POSITIVE, B_NEGATIVE, A_POSITIVE, A_NEGATIVE, O_POSITIVE, O_NEGATIVE, AB_POSITIVE, AB_NEGATIVE.";

            return buildErrorResponse("Bad Request",message,HttpStatus.BAD_REQUEST);
        }

        return buildErrorResponse("Bad Request","Malformed JSON Request",HttpStatus.BAD_REQUEST);


    }

    private ResponseEntity<Object> buildErrorResponse(String title,String message,HttpStatus status){
        Map<String,Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDate.now());
        errorBody.put("Status:",status.value());
        errorBody.put("error",title);
        errorBody.put("message",message);
        return new ResponseEntity<>(errorBody,status);
    }


}
