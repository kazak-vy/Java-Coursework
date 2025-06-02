package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
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
                        .requestMatchers("/**").authenticated()
                        .requestMatchers("/error", "/products/**").permitAll()
                        .anyRequest().authenticated())
                .build();
    }
}
