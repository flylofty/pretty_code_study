package com.lyn.pcode.exception;

public class FoodNameExistException extends RuntimeException {
    public FoodNameExistException() {
        super();
    }

    public FoodNameExistException(String message) {
        super(message);
    }

    public FoodNameExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public FoodNameExistException(Throwable cause) {
        super(cause);
    }

    protected FoodNameExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
