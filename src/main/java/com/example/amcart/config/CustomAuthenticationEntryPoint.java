package com.example.amcart.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        String body = """
                {
                "timeStamp" : "%s",
                "status" : "401",
                "error" : "unauthorized",
                "message" : "Invalid email or password",
                "path" : "%s"
                """.formatted(
                LocalDateTime.now(),
                request.getRequestURI()
        );
        response.getWriter().write(body);
    }
}
