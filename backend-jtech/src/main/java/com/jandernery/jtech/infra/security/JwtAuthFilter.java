package com.jandernery.jtech.infra.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jandernery.jtech.app.dtos.error.ErrorResponseDTO;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String TOKEN_COOKIE_NAME = "token";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    public JwtAuthFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService,
            ObjectMapper objectMapper
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            String token = extractTokenFromCookie(request);

            // 🔒 Sem cookie → request segue como não autenticada
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // 🔒 Token inválido (assinatura, estrutura, etc.)
            if (!jwtService.isTokenValid(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            // 🔒 Evita sobrescrever autenticação existente
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                filterChain.doFilter(request, response);
                return;
            }

            String username = jwtService.extractSubject(token);

            if (username == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // 🔐 Só agora acessa o banco
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException ex) {
            SecurityContextHolder.clearContext();
            writeTokenExpiredResponse(response);
        }
    }

    /**
     * Extrai o JWT do cookie HTTP-only
     */
    private String extractTokenFromCookie(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> TOKEN_COOKIE_NAME.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }

    /**
     * Define quais rotas NÃO passam pelo filtro
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.startsWith("/auth")
                || (path.equals("/users") && request.getMethod().equals("POST"));
    }

    /**
     * Retorno padronizado para token expirado
     */
    private void writeTokenExpiredResponse(HttpServletResponse response)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        ErrorResponseDTO error = new ErrorResponseDTO(
                "AUTH",
                "TOKEN_EXPIRED",
                HttpServletResponse.SC_UNAUTHORIZED,
                LocalDateTime.now()
        );

        response.getWriter()
                .write(objectMapper.writeValueAsString(error));
    }
}
