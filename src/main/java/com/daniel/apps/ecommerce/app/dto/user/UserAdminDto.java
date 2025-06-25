package com.daniel.apps.ecommerce.app.dto.user;

import com.daniel.apps.ecommerce.app.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserAdminDto {
    @NotBlank(message = "firstName is required")
    protected String firstName;

    @NotBlank(message = "lastName is required")
    protected String lastName;

    @Email(message = "provide a valid email")
    @NotBlank(message = "email is required")
    protected String email;

    @NotBlank(message = "password is required")
    @Size(min = 6, message = "password must not be less than 6 characters")
    protected String password;

    @NotNull(message = "role is required")
    private Role role;

    @NotNull(message = "enabled is required")
    protected boolean enabled;

    @NotNull(message = "nonLocked is required")
    protected boolean nonLocked;
}
