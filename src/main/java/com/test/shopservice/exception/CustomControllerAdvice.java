package com.test.shopservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice

public class CustomControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleInternalExceptions(Exception e) {

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(new ErrorMessage(httpStatus, e.getMessage(), formatBackendMessage(e)), httpStatus);
    }

    @ExceptionHandler(CustomBadRequestException.class)
    public ResponseEntity<ErrorMessage> handleBadResquestExceptions(Exception e) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorMessage(httpStatus, e.getMessage(), formatBackendMessage(e)), httpStatus);
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundExceptions(Exception e) {

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(new ErrorMessage(httpStatus, e.getMessage(), formatBackendMessage(e)), httpStatus);
    }

    private String formatBackendMessage(Exception e) {

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);

        return stringWriter.toString();
    }

}
