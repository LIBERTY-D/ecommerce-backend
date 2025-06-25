package com.daniel.apps.ecommerce.app.dto.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDto(
        @NotNull(message = "userId is required")
        Long userId,
        @NotBlank(message = "street required, if no" +
                "street type(n/a)") String street,
        @NotBlank(message = "city required, if no " +
                "city type(n/a)") String city,
        @NotBlank(message = "state required, if no " +
                "state type(n/a)") String state,
        @NotBlank(message = "postal code required, if no " +
                "postal code type(n/a)") String postalCode,
        @NotBlank(message = "Country required") String country) {
}
