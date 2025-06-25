package com.daniel.apps.ecommerce.app.exception;

public class NoSuchToken extends RuntimeException {
    public NoSuchToken(String tokenNotFound) {
        super(tokenNotFound);
    }
}
