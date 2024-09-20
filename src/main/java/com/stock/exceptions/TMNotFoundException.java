package com.stock.exceptions;

public class TMNotFoundException extends RuntimeException {
    public TMNotFoundException(String message){
        super(message);
    }

}
