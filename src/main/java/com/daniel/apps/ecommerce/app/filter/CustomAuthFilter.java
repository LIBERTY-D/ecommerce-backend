package com.daniel.apps.ecommerce.app.filter;

import com.daniel.apps.ecommerce.app.details.CustomUserDetail;
import com.daniel.apps.ecommerce.app.http.HttpResponse;
import com.daniel.apps.ecommerce.app.model.User;
import com.daniel.apps.ecommerce.app.service.jwt.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
public class CustomAuthFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return this.getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        HttpResponse<Object> httpResponse = HttpResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("Invalid email or password")
                .timeStamp(LocalDateTime.now())
                .build();
        objectMapper.writeValue(response.getOutputStream(), httpResponse);
        response.getOutputStream().flush();
        log.warn("Authentication failed: {}", failed.getMessage());
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("success login");
        User user = ((CustomUserDetail) authResult.getPrincipal()).user();
        HttpResponse<Object> httpResponse = HttpResponse.builder().build();
        httpResponse.setMessage("success login");
        httpResponse.setTimeStamp(LocalDateTime.now());
        httpResponse.setStatusCode(HttpStatus.OK.value());
        httpResponse.setStatus(HttpStatus.OK);

        String accessToken = jwtService.accessToken(user);
        String refreshToken = jwtService.refreshToken(user.getEmail());
        httpResponse.setData(Map.of("access_token", accessToken,
                "refresh_token", refreshToken));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        objectMapper.writeValue(response.getOutputStream(), httpResponse);

        response.getOutputStream().flush();
    }
}
