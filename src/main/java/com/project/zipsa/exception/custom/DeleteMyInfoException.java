package com.project.zipsa.exception.custom;

public class DeleteMyInfoException extends RuntimeException {

    public DeleteMyInfoException() {
        super();
    }

    public DeleteMyInfoException(String message) {
        super(message);
    }

    public DeleteMyInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteMyInfoException(Throwable cause) {
        super(cause);
    }

    protected DeleteMyInfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}