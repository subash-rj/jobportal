package com.example.jobportal.exception;

public class DuplicateApplicationException extends RuntimeException {
    public DuplicateApplicationException(String message){
        super(message);
    }
}
