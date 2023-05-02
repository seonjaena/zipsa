package com.project.zipsa.exception.custom;

public class NewPwNotSameException extends RuntimeException {

    public NewPwNotSameException() {
        super();
    }

    public NewPwNotSameException(String message) {
        super(message);
    }

    public NewPwNotSameException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewPwNotSameException(Throwable cause) {
        super(cause);
    }

    protected NewPwNotSameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}