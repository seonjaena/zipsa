package com.project.zipsa.exception.custom;

public class NoSuchRoomException extends RuntimeException {

    public NoSuchRoomException() {
        super();
    }

    public NoSuchRoomException(String message) {
        super(message);
    }

    public NoSuchRoomException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchRoomException(Throwable cause) {
        super(cause);
    }

    protected NoSuchRoomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
