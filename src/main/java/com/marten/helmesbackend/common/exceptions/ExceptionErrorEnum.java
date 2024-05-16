package com.marten.helmesbackend.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ExceptionErrorEnum {

    INVALID_USER_DATA("The submitted user data is invalid", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus httpStatus;

}
