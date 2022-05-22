package com.project.zipsa.exception.custom;

public class UnAuthenticatedException extends RuntimeException {

    public UnAuthenticatedException() {
        super();
    }

    public UnAuthenticatedException(String message) {
        super(message);
    }

    public UnAuthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnAuthenticatedException(Throwable cause) {
        super(cause);
    }

    protected UnAuthenticatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
