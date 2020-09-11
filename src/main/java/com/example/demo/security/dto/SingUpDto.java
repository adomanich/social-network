package com.example.demo.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.example.demo.security.ErrorMessage.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SingUpDto {

    @NotBlank(message = EMPTY_USERNAME)
    @Length(
            min = USERNAME_MIN_LENGTH,
            max = USERNAME_MAX_LENGTH,
            message = INVALID_USERNAME_LENGTH)
    private String firstName;

    @NotBlank(message = EMPTY_USERNAME)
    @Length(
            min = USERNAME_MIN_LENGTH,
            max = USERNAME_MAX_LENGTH,
            message = INVALID_USERNAME_LENGTH)
    private String lastName;

    @NotBlank(message = EMPTY_EMAIL)
    @Email(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = INVALID_EMAIL
    )
    private String email;

    @NotBlank
    @Pattern(
            regexp = "^(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*\\d+)(?=.*[~`!@#$%^&*()+=_\\-{}|:;”’?/<>,.\\]\\[]+).{8,}$",
            message = INVALID_PASSWORD
    )
    private String password;
}
