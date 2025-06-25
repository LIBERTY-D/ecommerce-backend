package com.daniel.apps.ecommerce.app.exception;

public class AuthHeaderRequired extends Throwable {
    public AuthHeaderRequired(String msg) {
        super(msg);
    }
}
