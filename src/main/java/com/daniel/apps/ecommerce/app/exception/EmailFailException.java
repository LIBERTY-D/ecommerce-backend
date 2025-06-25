package com.daniel.apps.ecommerce.app.exception;

public class EmailFailException extends RuntimeException{
    public EmailFailException(String emailFailureMessage) {
        super(emailFailureMessage);
    }
}
