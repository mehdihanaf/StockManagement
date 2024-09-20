package com.stock.exceptions;

import com.stock.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(CustomResponseException.class)
    public ResponseEntity<ErrorResponse> handleCustomResponseException(CustomResponseException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse("ERR_400", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}