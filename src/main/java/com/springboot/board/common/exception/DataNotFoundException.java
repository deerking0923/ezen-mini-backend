package com.springboot.board.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    public DataNotFoundException(String message) {
        super(message);
    }
} 