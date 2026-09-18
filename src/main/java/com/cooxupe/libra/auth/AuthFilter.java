package com.cooxupe.libra.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;

public class AuthFilter extends OncePerRequestFilter {

    private final String apiKeyHeaderName;
    private final String apiKeyHeaderValue;

    public AuthFilter(
            String apiKeyHeaderName,
            String apiKeyHeaderValue
    ) {
        this.apiKeyHeaderName = apiKeyHeaderName;
        this.apiKeyHeaderValue = apiKeyHeaderValue;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String path = request.getServletPath();

        return path.equals("/actuator/health")
                || path.equals("/error");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String receivedApiKey =
                request.getHeader(apiKeyHeaderName);

        if (!isApiKeyValid(receivedApiKey)) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            response.getWriter().write(
                    """
                    {
                      "status": 401,
                      "error": "Unauthorized",
                      "message": "API Key ausente ou invalida"
                    }
                    """
            );

            return;
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        "smart-1",
                        null,
                        Collections.emptyList()
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        try {
            filterChain.doFilter(request, response);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    private boolean isApiKeyValid(String receivedApiKey) {

        if (receivedApiKey == null
                || receivedApiKey.isBlank()
                || apiKeyHeaderValue == null
                || apiKeyHeaderValue.isBlank()) {

            return false;
        }

        byte[] receivedBytes =
                receivedApiKey.getBytes(StandardCharsets.UTF_8);

        byte[] expectedBytes =
                apiKeyHeaderValue.getBytes(StandardCharsets.UTF_8);

        return MessageDigest.isEqual(
                receivedBytes,
                expectedBytes
        );
    }
}