package com.daniel.apps.ecommerce.app.exception;

public class NoSuchCategoryException extends RuntimeException {
    public NoSuchCategoryException(String msg) {
        super(msg);
    }
}
