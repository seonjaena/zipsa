package com.project.zipsa.exception.custom;

public class NewPwCurPwSameException extends RuntimeException {

    public NewPwCurPwSameException() {
        super();
    }

    public NewPwCurPwSameException(String message) {
        super(message);
    }

    public NewPwCurPwSameException(String message, Throwable cause) {
        super(message, cause);
    }

    public NewPwCurPwSameException(Throwable cause) {
        super(cause);
    }

    protected NewPwCurPwSameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}