package com.shazzar.voteme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<String> mainResourceNotFoundException(ResourceNotFoundException ex) {
////        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//    }


}
