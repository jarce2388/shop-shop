package com.test.shopservice.log;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ErrorLogImpl implements ErrorLog{

    @Override
    public void register(HttpStatus httpStatus, HttpMethod httpMethod, String message, String fileName){

        String msg = httpMethod.name() + " : " + httpStatus.name() + " : " + httpStatus.value() + " : " + message;
        Logger logger = LogManager.getLogger(fileName);
        logger.error(msg);
    }
}
