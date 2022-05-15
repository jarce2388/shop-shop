package com.test.shopservice.exception;


public class CustomBadRequestException extends RuntimeException {

    public CustomBadRequestException() {
        super();
    }

    public CustomBadRequestException(String message) {
        super(message);
    }
}
