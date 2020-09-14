package com.example.demo.handler;

import com.example.demo.exeption.*;
import com.example.demo.security.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(UserAlreadyExistException.class)
    public final ResponseEntity<ErrorDto> handleUserNotFoundException
            (UserAlreadyExistException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDto error = new ErrorDto("CONFLICT", details);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IncorrectPasswordException.class)
    public final ResponseEntity<ErrorDto> handleIncorrectPasswordException
            (IncorrectPasswordException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDto error = new ErrorDto("BAD_REQUEST", details);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDto error = new ErrorDto("NOT_FOUND", details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(PublicationNotFoundException.class)
    public final ResponseEntity<ErrorDto> handlePublicationNotFoundException(PublicationNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDto error = new ErrorDto("NOT_FOUND", details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(PublicationAlreadyExistException.class)
    public final ResponseEntity<ErrorDto> handlePublicationAlreadyExistException(PublicationAlreadyExistException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDto error = new ErrorDto("CONFLICT", details);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(FollowerNotFoundException.class)
    public final ResponseEntity<ErrorDto> handleFollowerNotFoundException(FollowerNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDto error = new ErrorDto("NOT_FOUND", details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(FollowingNotFoundException.class)
    public final ResponseEntity<ErrorDto> handleFollowingNotFoundException(FollowingNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorDto error = new ErrorDto("NOT_FOUND", details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
