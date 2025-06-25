package com.daniel.apps.ecommerce.app.exception;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String msg) {
        super(msg);
    }
}
