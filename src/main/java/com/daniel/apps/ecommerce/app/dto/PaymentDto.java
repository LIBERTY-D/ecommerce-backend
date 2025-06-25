package com.daniel.apps.ecommerce.app.dto;

import com.daniel.apps.ecommerce.app.model.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;

public record PaymentDto(@NotNull(message = "Payment method required") PaymentMethod paymentMethod) {
}
