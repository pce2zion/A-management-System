package com.example.employee.system.employeesystem.exceptions;

public class MissingRequiredFieldException extends RuntimeException{
    public MissingRequiredFieldException() {
        super();
    }

    public MissingRequiredFieldException(String message) {
        super(message);
    }

    public MissingRequiredFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingRequiredFieldException(Throwable cause) {
        super(cause);
    }

    protected MissingRequiredFieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
