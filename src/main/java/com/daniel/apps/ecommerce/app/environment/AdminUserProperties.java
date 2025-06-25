package com.daniel.apps.ecommerce.app.environment;

import com.daniel.apps.ecommerce.app.model.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@ConfigurationProperties(prefix = "custom.admin")
@Getter
@Setter
public class AdminUserProperties {
    private String  admin_user;
   private  String  admin_password;
   private String admin_roles;

    public Set<Role> getAdminRolesAsEnumSet() {
        return Arrays.stream(admin_roles.split(","))
                .map(String::trim)
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }

}
