package com.example.employee.system.employeesystem.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    private final DetailsService detailsService;

    private final String path = "/api/v1/auth";
    private final String[] AUTH_WHITELIST = {
//            path + "/signin/**", path + "/forgot-password", path + "/reset-password",
//            path + "/register", path + "/verify-code", path + "/references",
//            "/v3/api-docs/**", "/configuration/**", "/swagger*/**",
//            "/swagger-ui/**", "/webjars/**",
//
//            path + "/register", path + "/verify-code", path + "/references",
//            path + "/forgot-password/**",
//            path + "/reset-password/**",
//            path + "/verify-token/**"
            path + "/register", path + "/authenticate", path + "/verify-token/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}
