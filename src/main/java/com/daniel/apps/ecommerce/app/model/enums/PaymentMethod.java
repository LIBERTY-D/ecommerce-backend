package com.daniel.apps.ecommerce.app.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PaymentMethod {
    CARD,
    PAYPAL;
    @JsonCreator()
    public static PaymentMethod fromString(String value) {
        return value == null ? null : PaymentMethod.valueOf(value.toUpperCase());
    }
}
