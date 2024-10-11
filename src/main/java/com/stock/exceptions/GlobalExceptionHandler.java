package com.stock.exceptions;

import com.stock.model.ErrorResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TMNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTMNotFoundException(TMNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse("ERR_404", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CustomResponseException.class)
    public ResponseEntity<ErrorResponse> handleCustomResponseException(CustomResponseException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse("ERR_400", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}