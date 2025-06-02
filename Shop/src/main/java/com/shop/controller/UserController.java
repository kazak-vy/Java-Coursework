package com.shop.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController
{
    @GetMapping("/profile")
    public String userProfile(Model model, OAuth2AuthenticationToken authentication)
    {
        var attributes = authentication.getPrincipal().getAttributes();

        String username = (String) attributes.get("preferred_username");
        String email = (String) attributes.get("email");
        String firstName = (String) attributes.get("given_name");
        String lastName = (String) attributes.get("family_name");
        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // e.g., "ROLE_admin"
                .collect(Collectors.toSet());

        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);

        return "profile.html";
    }
}
