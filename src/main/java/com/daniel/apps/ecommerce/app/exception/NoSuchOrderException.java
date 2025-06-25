package com.daniel.apps.ecommerce.app.exception;

public class NoSuchOrderException extends RuntimeException{
    public NoSuchOrderException(String msg) {
        super(msg);
    }
}
