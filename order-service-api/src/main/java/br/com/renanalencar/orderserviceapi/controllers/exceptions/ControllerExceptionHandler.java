package br.com.renanalencar.orderserviceapi.controllers.exceptions;

import models.exceptions.GenericFeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(GenericFeignException.class)
    ResponseEntity<Map> handlerGenericFeignException(final GenericFeignException exception) {
        return ResponseEntity.status(exception.getStatus()).body(exception.getError());
    }
}

