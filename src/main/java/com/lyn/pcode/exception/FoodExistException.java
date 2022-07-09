package com.lyn.pcode.exception;

public class FoodExistException extends RuntimeException {
    public FoodExistException() {
        super();
    }

    public FoodExistException(String message) {
        super(message);
    }

    public FoodExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public FoodExistException(Throwable cause) {
        super(cause);
    }

    protected FoodExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
