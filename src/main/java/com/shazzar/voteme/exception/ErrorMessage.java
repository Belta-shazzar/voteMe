package com.shazzar.voteme.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private int statusCode;
    private HttpStatus status;
    private String message;

}
