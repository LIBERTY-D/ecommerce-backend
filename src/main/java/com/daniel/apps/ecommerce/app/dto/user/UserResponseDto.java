package com.daniel.apps.ecommerce.app.dto.user;

import com.daniel.apps.ecommerce.app.dto.order.OrderItemDto;
import com.daniel.apps.ecommerce.app.model.Address;
import com.daniel.apps.ecommerce.app.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record UserResponseDto(Long id,
                              String firstName,
                              String lastName,
                              String email,
                              boolean enabled,
                              boolean isNonLocked,
                              Set<Role> roles,
                              @JsonIgnoreProperties({"user","orders"}) Address address,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt,
                              List <OrderItemDto> orders) {
}
