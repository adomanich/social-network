package com.example.demo.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PublicationNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PublicationNotFoundException(String message) {
        super(message);
    }
}
