package com.daniel.apps.ecommerce.app.service.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.daniel.apps.ecommerce.app.environment.JwtProperties;
import com.daniel.apps.ecommerce.app.model.User;
import com.daniel.apps.ecommerce.app.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtService {
    private final JwtProperties jwtProperties;

    private Algorithm algorithm() {

        return Algorithm.HMAC256(jwtProperties.getSecret());
    }

    public String accessToken(User user) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withPayload(Map.of(
                        "roles",
                        user.getRoles().stream().map(Role::name).toList(),
                        "id", user.getId().toString()
                ))
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getAccess_exp()))
                .sign(algorithm());
    }

    public String refreshToken(String payload) {
        return JWT.create().withSubject(payload).withIssuedAt(new Date(System.currentTimeMillis())).withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getRefresh_exp()))
                .sign(algorithm());
    }

    public DecodedJWT verifyToken(String token) {

        return JWT.require(algorithm()).build().verify(token);
    }

    public static String retrieveTokenFromHeader(String authHeader) {
        return authHeader.substring("Bearer ".length());
    }


}
