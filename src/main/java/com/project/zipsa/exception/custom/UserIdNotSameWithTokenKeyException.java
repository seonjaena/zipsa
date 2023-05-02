package com.project.zipsa.exception.custom;

public class UserIdNotSameWithTokenKeyException extends RuntimeException {

    public UserIdNotSameWithTokenKeyException() {
        super();
    }

    public UserIdNotSameWithTokenKeyException(String message) {
        super(message);
    }

    public UserIdNotSameWithTokenKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserIdNotSameWithTokenKeyException(Throwable cause) {
        super(cause);
    }

    protected UserIdNotSameWithTokenKeyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
