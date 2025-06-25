package com.daniel.apps.ecommerce.app.dto.user;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "firstName is required")
    protected String firstName;
    @NotBlank(message = "lastName is required")
    protected String lastName;
    @Email(message = "provide a valid email")
    @NotBlank(message = "email is required")
    protected String email;
//    @NotBlank(message = "password is required")
   // @Size(min = 6, message = "password must not be less than 6 characters")
    protected String password;
    protected String phoneNumber;
}
