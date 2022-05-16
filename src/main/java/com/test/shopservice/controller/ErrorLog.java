package com.test.shopservice.controller;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public interface ErrorLog {

    public void register(HttpStatus httpStatus, HttpMethod httpMethod, String message);
}
