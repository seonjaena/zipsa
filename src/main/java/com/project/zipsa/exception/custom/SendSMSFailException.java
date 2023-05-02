package com.project.zipsa.exception.custom;

public class SendSMSFailException extends RuntimeException {

    public SendSMSFailException() {
        super();
    }

    public SendSMSFailException(String message) {
        super(message);
    }

    public SendSMSFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public SendSMSFailException(Throwable cause) {
        super(cause);
    }

    protected SendSMSFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
