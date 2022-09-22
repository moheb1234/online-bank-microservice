package com.example.userservice.exception;

import feign.FeignException;
import org.postgresql.util.PSQLException;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.management.InstanceNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(InstanceNotFoundException.class)
    public ResponseEntity<String> e1(InstanceNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<String> e2(PSQLException e) {
        return new ResponseEntity<>(e.getMessage(),HttpStatus.CONTINUE);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> e3(FeignException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.valueOf(e.status()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> e4(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            message.append(violation.getMessage().concat(";"));
        }
        return new ResponseEntity<>(message.toString(),HttpStatus.BAD_REQUEST);
    }
}
