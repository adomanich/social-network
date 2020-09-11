package com.example.demo.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.example.demo.security.SecureConstant.EXPIRATION_TIME;
import static com.example.demo.security.SecureConstant.SECRET;

@Service
public class JwtTool {

    public String createAccessToken(String email) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }
}
