package com.test.shopservice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ErrorMessage {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date date;
    private String httpStatus;
    private String message;
    private int code;
    private String backendMessage;


    public ErrorMessage() {
        date = new Date();
    }

    public ErrorMessage(HttpStatus httpStatus, String message) {

        this();
        this.httpStatus = httpStatus.name();
        this.code = httpStatus.value();
        this.message = message;
    }

    public ErrorMessage(HttpStatus httpStatus, String message, String backendMessage) {

        this(httpStatus, message);
        this.backendMessage = backendMessage;
    }

}
