package com.daniel.apps.ecommerce.app.exception;

public class NoSuchAddressException extends RuntimeException {
    public NoSuchAddressException(String msg) {
        super(msg);
    }
}
