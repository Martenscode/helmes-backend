package com.marten.helmesbackend.common.exceptions;

public class InvalidUserDataException extends DynamicPublicException {

    public InvalidUserDataException() {
        super(ExceptionErrorEnum.INVALID_USER_DATA);
    }

}
