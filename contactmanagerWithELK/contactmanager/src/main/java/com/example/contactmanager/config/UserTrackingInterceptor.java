package com.example.contactmanager.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.UUID;

@Component
public class UserTrackingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 1. Generate a unique Correlation ID for this specific request
        String correlationId = UUID.randomUUID().toString();
        MDC.put("correlationId", correlationId);

        // 2. Try to find userId in the URL (e.g., /contacts/5)
        String path = request.getRequestURI();
        String[] parts = path.split("/");
        if (parts.length > 2 && parts[1].equals("contacts")) {
            MDC.put("userId", parts[2]);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // CRITICAL: Clean up after the request is done to prevent memory leaks
        MDC.clear();
    }
}