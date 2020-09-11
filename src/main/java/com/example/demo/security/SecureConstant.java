package com.example.demo.security;

public class SecureConstant {
    public static final String SECRET = "12345";
    public static final long EXPIRATION_TIME = 900_000; // 15 mins
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
