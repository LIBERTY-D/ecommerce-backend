package com.daniel.apps.ecommerce.app.environment;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "custom.jwt")
@Getter
@Setter
public class JwtProperties {
    private String secret;
    private Long access_exp;
    private Long refresh_exp;

}
