package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    //for adding login and log out forms
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.oauth2Login(Customizer.withDefaults());
        http.logout(logout -> logout
            .logoutSuccessHandler((request, response, authentication) -> {
                String keycloakLogoutUrl = "http://localhost:8080/realms/Test/protocol/openid-connect/logout" +
                        "?redirect_uri=http://localhost:8081/logged-out";

                response.sendRedirect(keycloakLogoutUrl);
            })
    );
        return http
                .authorizeHttpRequests(c -> c
                        .requestMatchers("/error").permitAll() //unauthorized users can only see errors
                        .anyRequest().authenticated()) //authorized can see everything
                .build();
    }
}
