package com.daniel.apps.ecommerce.app.dto.category;

import jakarta.validation.constraints.NotBlank;
public record CategoryDto(@NotBlank(message = "categoryName  is required") String categoryName) {
}
