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
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ErrorMessage> alreadyExistException(AlreadyExistException ex) {
        ErrorMessage message = new ErrorMessage(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> illegalArgumentException(IllegalArgumentException ex) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST, ex.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }


}
