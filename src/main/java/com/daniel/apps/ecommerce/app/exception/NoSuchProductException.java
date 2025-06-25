package com.daniel.apps.ecommerce.app.exception;

public class NoSuchProductException extends RuntimeException {
    public NoSuchProductException(String msg) {
        super(msg);
    }
}
