package com.daniel.apps.ecommerce.app.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductDto(
        @NotBlank(message = "Product name is required")
        String name,

        @NotBlank(message = "Product description is required")
        String description,

        @NotNull(message = "Product price is required")
        BigDecimal price,

        @NotNull(message = "Product quantity is required")
        Integer quantity,

        @NotNull(message="Category Id is required")
         Long categoryId


) {}
