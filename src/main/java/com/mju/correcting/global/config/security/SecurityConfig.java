package com.mju.correcting.global.config.security;

import com.mju.correcting.global.config.security.authentication.JwtAuthenticationEntryPoint;
import com.mju.correcting.global.config.security.filter.JwtFilter;
import com.mju.correcting.global.config.security.handler.JwtAccessDeniedHandler;
import com.mju.correcting.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)

                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                .requestMatchers( "/api/v1/users/login").permitAll()
                .requestMatchers( "/api/v1/users/{loginId}/exists").permitAll()
                .requestMatchers( "/api/v1/users/token").permitAll()
                .requestMatchers("/api/v1/**").hasRole("USER")
                .anyRequest().permitAll()

                .and()
                .addFilterBefore(new JwtFilter(tokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
