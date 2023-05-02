package com.project.zipsa.exception.custom;

public class RefreshTokenExpireException extends RuntimeException {

    public RefreshTokenExpireException() {
        super();
    }

    public RefreshTokenExpireException(String message) {
        super(message);
    }

    public RefreshTokenExpireException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshTokenExpireException(Throwable cause) {
        super(cause);
    }

    protected RefreshTokenExpireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}