package com.dandelion.backend.security;

import com.dandelion.backend.entities.enumType.RoleBase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomerUserDetailsService customerUserDetailsService;

    private final LogoutHandler logoutHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // rest api. no csrf
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // using jwt token, no session needed.
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
                .securityMatcher("/api/**")
                .authorizeHttpRequests((authorize) -> authorize
                        // Auth
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // Product
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                        .requestMatchers("/api/v1/products/**").hasAnyAuthority(RoleBase.ADMIN.name(), RoleBase.MANAGER.name())

                        // Category
                        .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                        .requestMatchers("/api/v1/categories/**").hasAnyAuthority(RoleBase.ADMIN.name(), RoleBase.MANAGER.name())

                        // Cart
                        .requestMatchers("/api/v1/carts/**").hasAuthority(RoleBase.CUSTOMER.name())

                        // Address
                        .requestMatchers("/api/v1/address/**").hasAuthority(RoleBase.CUSTOMER.name())

                        // Shipping method
                        .requestMatchers(HttpMethod.GET, "/api/v1/shipping-methods/**").permitAll()
                        .requestMatchers("/api/v1/shipping-methods/**").hasAnyAuthority(RoleBase.ADMIN.name(), RoleBase.MANAGER.name())

                        // Payment method
                        .requestMatchers(HttpMethod.GET, "/api/v1/payment-methods/**").permitAll()
                        .requestMatchers("/api/v1/payment-methods/**").hasAnyAuthority(RoleBase.ADMIN.name(), RoleBase.MANAGER.name())

                        // Order status
                        .requestMatchers(HttpMethod.GET, "/api/v1/order-status/**").hasAuthority(RoleBase.CUSTOMER.name())

                        // Place order
                        .requestMatchers("/api/v1/place-order/**").hasAuthority(RoleBase.CUSTOMER.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/orders").hasAuthority(RoleBase.CUSTOMER.name())
                        .requestMatchers("/api/v1/orders/**").hasAnyAuthority(RoleBase.ADMIN.name(), RoleBase.MANAGER.name())

                        // User
                        .requestMatchers("/api/v1/users/**").hasAuthority(RoleBase.ADMIN.name())
                        .anyRequest().authenticated()
                );
        // If request No-Auth return 401 instead 403.
        http.exceptionHandling(customize -> customize.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        http.logout(logout -> logout
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // jwt token Filter is added before id/password Filter

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
