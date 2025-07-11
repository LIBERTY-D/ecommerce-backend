package com.daniel.apps.ecommerce.app.db;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class EnvLogger {

    @Value("${spring.datasource.url:NOT SET}")
    private String dbUrl;

    @Value("${spring.datasource.username:NOT SET}")
    private String dbUser;

    @Value("${spring.datasource.password:NOT SET}")
    private String dbPassword;

    @PostConstruct
    public void logEnvVariables() {
        System.out.println("=== Environment Variables ===");
        System.out.println("spring.datasource.url: " + dbUrl);
        System.out.println("spring.datasource.username: " + dbUser);
        System.out.println("spring.datasource.password: " + dbPassword);
        System.out.println("=============================");
    }
}
