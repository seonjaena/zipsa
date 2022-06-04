package com.project.zipsa.exception.custom;

public class BadUserPhoneException extends RuntimeException {

    public BadUserPhoneException() {
        super();
    }

    public BadUserPhoneException(String message) {
        super(message);
    }

    public BadUserPhoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadUserPhoneException(Throwable cause) {
        super(cause);
    }

    protected BadUserPhoneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
