package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.exception.ExistingCarException;

@RestControllerAdvice
public class CarControllerAdvise {
@ExceptionHandler
public ResponseEntity<?> handleCarNotFoundException(ExistingCarException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                         .body(ex.getMessage());
}

}
