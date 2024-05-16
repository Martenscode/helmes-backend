package com.marten.helmesbackend.common.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleGlobalError(Exception e) {
        log.error("{}", e.getMessage(), e);
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({DynamicPublicException.class})
    public ResponseEntity<Object> handleBusinessException(DynamicPublicException e) {
        log.warn("{}", e.getErrorMessage());
        //Some structured error response format could be used + error codes etc
        return new ResponseEntity<>(e.getErrorMessage(), e.getHttpStatus());
    }

}
