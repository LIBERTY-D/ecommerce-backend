package com.daniel.apps.ecommerce.app.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PasswordDto {
    @NotBlank(message = "password is required")
     @Size(min = 6, message = "password must not be less than 6 characters")
    protected String password;
}
