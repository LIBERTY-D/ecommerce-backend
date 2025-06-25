package com.daniel.apps.ecommerce.app.dto.order;

import com.daniel.apps.ecommerce.app.dto.PaymentDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderDto(@NotNull(message = "Payment method required")
                       @Valid PaymentDto payment,
                       @NotNull(message = "User id required") Long userId,
                       @NotNull(message = "Orders are required") @Valid List<CartItem> cartItems)  {
}
