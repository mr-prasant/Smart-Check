package com.smartcheck.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {
    private final JWTRequestFilter filter;
    private final JWTAuthEntryPoint authEntryPoint;
    private final MyAccessDeniedHandler accessDenied;

    public SecurityConfiguration(JWTRequestFilter filter, JWTAuthEntryPoint authEntryPoint, MyAccessDeniedHandler accessDenied) {
        this.filter = filter;
        this.authEntryPoint = authEntryPoint;
        this.accessDenied = accessDenied;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/v1/p/**").permitAll()
                                .requestMatchers("/api/v1/u/**").hasRole("USER")
                                .requestMatchers("/api/v1/a/**").hasRole("ADMIN")
                                .requestMatchers("/api/v1/c/**").hasAnyRole("USER", "ADMIN")
                                .anyRequest().authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authEntryPoint).accessDeniedHandler(accessDenied))
                .csrf(csrf -> csrf.disable())
                .httpBasic(withDefaults())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
