package com.test.shopservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice

public class CustomControllerAdvice {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorMessage> handleNullPointException(Exception e) {

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(new ErrorMessage(httpStatus, e.getMessage()), httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> internalExceptions(Exception e) {

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        return new ResponseEntity<>(new ErrorMessage(httpStatus, e.getMessage(), backendMessage(e)), httpStatus);
    }

    @ExceptionHandler(CustomBadRequestException.class)
    public ResponseEntity<ErrorMessage> badResquestExceptions(Exception e) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(new ErrorMessage(httpStatus, e.getMessage(), backendMessage(e)), httpStatus);
    }

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundExceptions(Exception e) {

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(new ErrorMessage(httpStatus, e.getMessage(), backendMessage(e)), httpStatus);
    }

    private String backendMessage(Exception e) {

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);

        return stringWriter.toString();
    }

}
