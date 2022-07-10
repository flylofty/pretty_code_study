package com.lyn.pcode.exception;

public class GlobalExistException extends RuntimeException {
    public GlobalExistException() {
        super();
    }

    public GlobalExistException(String message) {
        super(message);
    }

    public GlobalExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public GlobalExistException(Throwable cause) {
        super(cause);
    }

    protected GlobalExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
