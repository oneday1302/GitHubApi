package com.testtask.githubapi.controller;

import com.testtask.githubapi.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<ErrorResponse> notFoundException(HttpClientErrorException.NotFound ex) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(new ErrorResponse(ex.getStatusCode().value(), "User not exist."));
    }
}
