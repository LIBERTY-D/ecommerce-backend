package com.daniel.apps.ecommerce.app.exception;

public class ProductQuantityExceededException extends RuntimeException {
    public ProductQuantityExceededException(String msg) {
        super(msg);
    }
}
