package com.daniel.apps.ecommerce.app.dto.product;

import com.daniel.apps.ecommerce.app.model.Category;
import com.daniel.apps.ecommerce.app.model.Payment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

public record ProductBoughtDto(
        Long productId,
        String name,
        String description,
        Integer qty,
        BigDecimal totalPrice,
        BigDecimal price,
        @JsonIgnoreProperties({"products","id"}) Category category,
        Payment payment
) {
}
