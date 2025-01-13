package com.api.gestor_clientes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        StringBuilder errorMessages = new StringBuilder("Datos inválidos: ");
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorMessages.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append("; ");
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorMessages.toString());
    }
}