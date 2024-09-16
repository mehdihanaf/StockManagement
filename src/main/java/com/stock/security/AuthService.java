package com.stock.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String AUTH_TOKEN_HEADER_NAME = "api-key";
    private final AuthProperties authProperties;


    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs",
            "/swagger-ui",
            "/actuator",
            "/actuator/**"
    };

    public Authentication getAuthentication(HttpServletRequest request) {
        var path = request.getRequestURL().toString();
        if(!stringContainsItemFromList(path, AUTH_WHITELIST)) {
            var apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);

            if (apiKey==null || !apiKey.equals(authProperties.getApiKey())) {
                throw new BadCredentialsException("Invalid API Key");
            }

            return new ApiKeyAuth(apiKey, AuthorityUtils.NO_AUTHORITIES);
        }
        return new ApiKeyAuth("superSecretKey", AuthorityUtils.NO_AUTHORITIES);
    }

    public static boolean stringContainsItemFromList(String path, String[] whitelist) {
        return Arrays.stream(whitelist).anyMatch(path::contains);
    }



}
