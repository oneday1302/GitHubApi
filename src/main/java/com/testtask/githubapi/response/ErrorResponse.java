package com.testtask.githubapi.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorResponse {

    private int status;

    private String message;
}
