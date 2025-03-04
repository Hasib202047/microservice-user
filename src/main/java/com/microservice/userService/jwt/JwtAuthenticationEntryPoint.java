package com.microservice.userService.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        // Create a structured JSON response
        String jsonResponse = String.format(
                "{\"timestamp\": %d, \"statusCode\": %d, \"status\": \"Unauthorized\", \"message\": \"Authentication token is missing or invalid\"}",
                new Date().getTime(),
                HttpServletResponse.SC_UNAUTHORIZED
        );
        response.getWriter().write(jsonResponse);

    }
}
