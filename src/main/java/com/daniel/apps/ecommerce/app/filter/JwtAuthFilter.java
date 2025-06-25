package com.daniel.apps.ecommerce.app.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("In OncePerRequestFilter, processing request path: {}", request.getServletPath());
        // Skip filter for public endpoints
        if (isPublicEndpoint(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ") && SecurityContextHolder.getContext().getAuthentication() == null) {
            String token = JwtService.retrieveTokenFromHeader(authHeader);
            try {
                DecodedJWT decodedJWT = jwtService.verifyToken(token);
                String email = decodedJWT.getSubject();

                CustomUserDetail customUserDetail = (CustomUserDetail) userDetailsService.loadUserByUsername(email);
                User user = customUserDetail.user();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,
                        null,
                        user.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.name()))
                                .toList()
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
                return;
            } catch (Exception exp) {
                log.error("Something wrong with the token: {}",
                        exp.getMessage());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                HttpResponse<Object> httpResponse = HttpResponse.builder().build();
                httpResponse.setMessage(exp.getMessage());
                httpResponse.setTimeStamp(LocalDateTime.now());
                httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
                httpResponse.setStatus(HttpStatus.UNAUTHORIZED);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                objectMapper.writeValue(response.getOutputStream(), httpResponse);
                response.getOutputStream().flush();
                return;
            }
        } else {
            log.warn("Missing or invalid Authorization header");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            HttpResponse<Object> httpResponse = HttpResponse.builder().build();
            httpResponse.setMessage("Unauthorized");
            httpResponse.setTimeStamp(LocalDateTime.now());
            httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            objectMapper.writeValue(response.getOutputStream(), httpResponse);
            response.getOutputStream().flush();
            return;
        }
    }

    private boolean isPublicEndpoint(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();

        return (path.equals("/api/v1/users/login") && method.equals("POST")) ||
                (path.equals("/api/v1/users/refresh/token") && method.equals(
                        "  POST")) ||
                (path.equals("/api/v1/users/verify") && method.equals("GET")) ||
                (path.equals("/api/v1/users") && (method.equals("POST")) ||
                        (path.equals("/api/v1/products") && method.equals(
                                "GET")) || (path.equals("/api/v1/contact") && method.equals("POST")));
    }

}
