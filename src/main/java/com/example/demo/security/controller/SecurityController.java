package com.example.demo.security.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.entity.request.Login;
import com.example.demo.exeption.IncorrectPasswordException;
import com.example.demo.exeption.UserAlreadyExistException;
import com.example.demo.exeption.UserNotFoundException;
import com.example.demo.security.dto.SingUpDto;
import com.example.demo.security.dto.SuccessSingInDto;
import com.example.demo.security.service.JwtTool;
import com.example.demo.security.service.SecurityService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @Autowired
    private SecurityService securityService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTool jwtTool;

    @PostMapping("/api/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody SingUpDto singUpDto) {
        UserDto userDto = new UserDto();
        try {
            UserDetails user = securityService.loadUserByUsername(singUpDto.getEmail());
            if (user != null) {
                throw new UserAlreadyExistException("User with " + user.getUsername() + " email already exist");
            }
        } catch (UserNotFoundException ex) {
            userDto = securityService.singUp(singUpDto);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PostMapping("/api/signIn")
    public ResponseEntity<?> signIp(@RequestBody Login loginModel) {
        securityService.loadUserByUsername(loginModel.getEmail());
        if (!securityService.isPasswordValid(loginModel)) {
            throw new IncorrectPasswordException("The " + loginModel.getPassword() + " password is incorrect for " + loginModel.getEmail() + " email");
        }
        User user1 = userService.getUserByEmail(loginModel.getEmail());
        String accessToken = jwtTool.createAccessToken(loginModel.getEmail());
        SuccessSingInDto successSingInDto = new SuccessSingInDto();
        successSingInDto.setAccessToken(accessToken);
        successSingInDto.setName(user1.getFirstName());
        successSingInDto.setUserId(user1.getId());
        return ResponseEntity.status(HttpStatus.OK).body(successSingInDto);
    }
}