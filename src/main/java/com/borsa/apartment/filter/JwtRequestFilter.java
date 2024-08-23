package com.borsa.apartment.filter;

import com.borsa.apartment.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Autowired
    public JwtRequestFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    private static final List<String> EXCLUDED_ENDPOINTS = Arrays.asList(
            "/api/auth/tokens",
            "/api/users"
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        String method = request.getMethod();
        return "POST".equalsIgnoreCase(method) && EXCLUDED_ENDPOINTS.contains(path);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        String id;
        String token;

        try {
            if(authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new JwtException("Unauthorized request.");
            }
            token = authHeader.substring(7);
            id = jwtService.extractId(token);
            if (jwtService.isTokenValid(token, id)) {
                PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(id, null, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (JwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(e.getMessage());
            return; // Stop further processing
        }
        chain.doFilter(request, response);
    }
}
