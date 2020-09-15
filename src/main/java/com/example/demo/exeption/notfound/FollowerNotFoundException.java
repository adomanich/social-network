package com.example.demo.exeption.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FollowerNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FollowerNotFoundException(String message) {
        super(message);
    }
}
