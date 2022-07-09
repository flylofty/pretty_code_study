package com.lyn.pcode.exception;

public class FoodAlreadyExistException extends RuntimeException {
    public FoodAlreadyExistException() {
        super();
    }

    public FoodAlreadyExistException(String message) {
        super(message);
    }

    public FoodAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public FoodAlreadyExistException(Throwable cause) {
        super(cause);
    }

    protected FoodAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
