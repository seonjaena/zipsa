package com.project.zipsa.exception.custom;

public class BadValidDeviceCodeException extends RuntimeException {

    public BadValidDeviceCodeException() {
        super();
    }

    public BadValidDeviceCodeException(String message) {
        super(message);
    }

    public BadValidDeviceCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadValidDeviceCodeException(Throwable cause) {
        super(cause);
    }

    protected BadValidDeviceCodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
