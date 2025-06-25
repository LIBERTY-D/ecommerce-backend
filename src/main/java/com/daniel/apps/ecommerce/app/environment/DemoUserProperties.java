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
@ConfigurationProperties(prefix = "custom.demo")
@Getter
@Setter
public class DemoUserProperties {
    private String  demo_user;
    private  String  demo_password;
    private String demo_role;
    public Role role() {
        return Role.valueOf(demo_role);
    }

}