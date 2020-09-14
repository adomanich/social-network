package com.example.demo.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Login {
    @JsonProperty(value = "email")
    private String email;
    @JsonProperty(value = "password")
    private String password;
}
