package com.marten.helmesbackend.common.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DynamicPublicException extends RuntimeException{

    private final String errorMessage;
    private final HttpStatus httpStatus;

    public DynamicPublicException(ExceptionErrorEnum exceptionErrorEnum) {
        this.errorMessage = exceptionErrorEnum.getMessage();
        this.httpStatus = exceptionErrorEnum.getHttpStatus();
    }

}