package com.stock.exceptions;

public class CustomResponseException extends RuntimeException{
    public CustomResponseException(String message) {
        super(message);
    }
}
