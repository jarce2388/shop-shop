package com.test.shopservice.log;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public interface ErrorLog {
    public void register(HttpStatus httpStatus, HttpMethod httpMethod, String message);
}
