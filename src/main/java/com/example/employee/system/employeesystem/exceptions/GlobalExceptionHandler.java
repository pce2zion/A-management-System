package com.example.employee.system.employeesystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorMessages> UserAlreadyExistsException(UserAlreadyExistsException e, WebRequest request){

        ErrorMessages message = new ErrorMessages(HttpStatus.ALREADY_REPORTED, e.getMessage());

        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(message);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNameNotFoundException.class)
    public ResponseEntity<ErrorMessages> UserNameNotFoundException(UserNameNotFoundException e, WebRequest request){

        ErrorMessages message = new ErrorMessages(HttpStatus.NOT_FOUND, e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MissingRequiredFieldException.class)
    public ResponseEntity<ErrorMessages> MissingRequiredFieldException(MissingRequiredFieldException e, WebRequest request){

        ErrorMessages message = new ErrorMessages(HttpStatus.NOT_FOUND, e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorMessages> PasswordMismatchException(PasswordMismatchException e, WebRequest request){

        ErrorMessages message = new ErrorMessages(HttpStatus.NOT_FOUND, e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

}
