package com.dev.armond.common.config;

import com.dev.armond.common.filter.JwtAuthenticationFilter;
import com.dev.armond.common.util.JwtTokenProvider;
import com.dev.armond.member.service.impl.CustomMemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomMemberDetailsService memberDetailsService;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final JwtTokenProvider jwtTokenProvider;

    private final static String[] WHITELIST = {
            "/auth/**",           // 인증 관련 API
            "/swagger-ui/**",     // Swagger UI
            "/v3/api-docs/**"     // API 문서
            // 다른 공개 API들...
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(WHITELIST).permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        builder.userDetailsService(memberDetailsService)
                .passwordEncoder(passwordEncoderConfig.passwordEncoder());

        return builder.build();
    }
}
