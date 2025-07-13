package com.example.jobportal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation errors in DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationErrors(MethodArgumentNotValidException ex){

        Map<String,String> errors = new HashMap<String,String>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Handle duplicate email
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExists(EmailAlreadyExistsException ex){
        return new ResponseEntity<>("Something went wrong: "+ex.getMessage(), HttpStatus.CONFLICT);


    }

    // Handle no resource error
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceUnavailable(ResourceNotFoundException ex){
        return new ResponseEntity<>("Something went wrong: "+ex.getMessage(), HttpStatus.NOT_FOUND);


    }

    // Handle duplicate insertion error
    @ExceptionHandler(DuplicateApplicationException.class)
    public ResponseEntity<String> handleDuplicateAppException(DuplicateApplicationException ex){
        return new ResponseEntity<>("Something went wrong: "+ex.getMessage(), HttpStatus.CONFLICT);


    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex){
        return new ResponseEntity<>("Something went wrong: "+ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
