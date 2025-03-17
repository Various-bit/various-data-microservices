package org.example.authorization.config.error;

import org.example.authorization.config.error.exception.NotAuthorized;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchEmailAlreadyRegistered(NotAuthorized e) {
        return new ResponseEntity<>(new AppError("401", e.getMessage()), HttpStatus.CONFLICT);
    }
}
