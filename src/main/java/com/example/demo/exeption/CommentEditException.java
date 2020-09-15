package com.example.demo.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CommentEditException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CommentEditException(String message) {
        super(message);
    }
}
