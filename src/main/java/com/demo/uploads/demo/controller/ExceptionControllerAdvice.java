package com.demo.uploads.demo.controller;

import com.demo.uploads.demo.entity.error.EmailExistsException;
import com.demo.uploads.demo.entity.error.ForbiddenException;
import com.demo.uploads.demo.entity.error.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<String> handleEmailExistsException(EmailExistsException exception) {
        return ResponseEntity.badRequest().body(String.format("There is an account with that email adress: %s", exception.getEmail()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> handleForbiddenException(ForbiddenException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(String.format("Forbidden to access resource %s", exception.getFileId()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity.notFound().build();
    }
}
