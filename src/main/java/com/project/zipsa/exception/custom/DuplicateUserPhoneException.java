package com.project.zipsa.exception.custom;

public class DuplicateUserPhoneException extends RuntimeException {

    public DuplicateUserPhoneException() {
        super();
    }

    public DuplicateUserPhoneException(String message) {
        super(message);
    }

    public DuplicateUserPhoneException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateUserPhoneException(Throwable cause) {
        super(cause);
    }

    protected DuplicateUserPhoneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
