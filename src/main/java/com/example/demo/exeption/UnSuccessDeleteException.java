package com.example.demo.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class UnSuccessDeleteException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnSuccessDeleteException(String message) {
        super(message);
    }
}
