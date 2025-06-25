package com.daniel.apps.ecommerce.app.dto.order;

import jakarta.validation.constraints.NotNull;

public record CartItem (@NotNull(message = "quantity is required") Integer quantity,
                        @NotNull(message = "product Id is required") Long productId){
}
