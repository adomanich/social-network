package com.example.demo.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.example.demo.security.ErrorMessage.INVALID_EMAIL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SingInDto {
    @NotBlank
    @Email(message = INVALID_EMAIL)
    private String email;

    @NotBlank
    private String password;
}
