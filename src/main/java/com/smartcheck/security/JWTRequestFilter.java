package com.smartcheck.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.smartcheck.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public JWTRequestFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        String email = null, jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);

            try {

                email = jwtUtil.extractEmail(jwt);

            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
                response.setContentType("application/json");
                response.getWriter().write(String.format("""
                        {
                            "error": "Unauthorized: token expired",
                            "reason": "%s",
                        }
                        """, e.getClass()));
                return;
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write(String.format("""
                        {
                            "error": "Unauthorized: missing or invalid token",
                            "reason": "%s",
                        }
                        """, e.getClass()));
                return;
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            try {
                jwtUtil.validateToken(email, jwt);
                List<String> roles = jwtUtil.extractRoles(jwt);
                List<GrantedAuthority> authorities = new ArrayList<>();
                for (String role : roles) {
                    authorities.add(new SimpleGrantedAuthority(role));
                }

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
                response.setContentType("application/json");
                response.getWriter().write(String.format("""
                        {
                            "error": "Unauthorized: token expired",
                            "reason": "%s",
                        }
                        """, e.getClass()));
                return;
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write(String.format("""
                        {
                            "error": "Unauthorized: missing or invalid token",
                            "reason": "%s",
                        }
                        """, e.getClass()));
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
