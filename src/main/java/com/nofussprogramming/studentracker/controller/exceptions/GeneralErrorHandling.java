package com.nofussprogramming.studentracker.controller.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GeneralErrorHandling extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { NotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Resource was not found";
        return handleExceptionInternal(ex, bodyOfResponse,new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { DatabaseErrorException.class })
    protected ResponseEntity<Object> handleDBError(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "An internal database error has occurred";
        return handleExceptionInternal(ex, bodyOfResponse,new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
