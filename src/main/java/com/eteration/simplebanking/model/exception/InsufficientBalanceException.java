package com.eteration.simplebanking.model.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// This class is a place holder you can change the complete implementation
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InsufficientBalanceException extends Exception {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}