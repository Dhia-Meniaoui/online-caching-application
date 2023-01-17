package com.example.clientsservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CustomException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    public CustomException(String message,Exception e) {
        super(message,e);
    }
    public CustomException(String message){
        super(message);
    }
}