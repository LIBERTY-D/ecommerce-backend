package com.daniel.apps.ecommerce.app.environment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



@Component
@ConfigurationProperties(prefix = "custom")
@Getter
@Setter
public class AllowedWebsitesProperties{
    private String  cors;
    public List<String> allowedWebsites() {
        return Arrays.stream(cors.split(",")).map(String::trim).collect(Collectors.toList());
    }

}