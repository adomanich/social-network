package com.example.demo.security.dto;

import java.util.List;

public class ErrorDto {

    public ErrorDto(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }

    private String message;
    private List<String> details;
}
