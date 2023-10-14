package com.dandelion.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomerUserDetailsService customerUserDetailsService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(c -> c.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();

                    config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setExposedHeaders(List.of("Authorization"));
                    config.setMaxAge(3600L);

                    return config;
                }))
                .authorizeHttpRequests((authorize) -> authorize
                        // Auth
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // Product
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                        .requestMatchers("/api/v1/products/**").hasAnyAuthority("ADMIN", "MANAGER")

                        // Category
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                        .requestMatchers("/api/v1/categories/**").hasAnyAuthority("ADMIN", "MANAGER")

                        // Cart
                        .requestMatchers("/api/v1/carts/**").permitAll()

                        // Address
                        .requestMatchers("/api/v1/address/**").permitAll()

                        // Shipping method
                        .requestMatchers(HttpMethod.GET, "/api/v1/shipping-methods/**").permitAll()
                        .requestMatchers("/api/v1/shipping-methods/**").hasAnyAuthority("ADMIN", "MANAGER")

                        // Payment method
                        .requestMatchers(HttpMethod.GET, "/api/v1/payment-methods/**").permitAll()
                        .requestMatchers("/api/v1/payment-methods/**").hasAnyAuthority("ADMIN", "MANAGER")

                        // Order status
                        .requestMatchers(HttpMethod.GET, "/api/v1/order-status/**").permitAll()

                        // Place order
                        .requestMatchers("/api/v1/place-order/**").hasAuthority("CUSTOMER")
                        .requestMatchers("/api/v1/orders").hasAuthority("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/orders/**").hasAnyAuthority("ADMIN", "MANAGER")

                        // User
                        // .requestMatchers("/api/v1/users").permitAll()
                        .anyRequest().authenticated()
                )
//                .logout(logout -> logout.logoutUrl("/api/v1/auth/logout")
//                        .addLogoutHandler(logoutHandler)
//                        .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext())))

        ;

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
