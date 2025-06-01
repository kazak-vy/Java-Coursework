package com.shop.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController
{
    @GetMapping("/profile")
    public String userProfile(Model model, OAuth2AuthenticationToken authentication) {
        var attributes = authentication.getPrincipal().getAttributes();

        // Example values from Keycloak
        String username = (String) attributes.get("preferred_username");
        String email = (String) attributes.get("email");
        String firstName = (String) attributes.get("given_name");
        String lastName = (String) attributes.get("family_name");
        Set<String> roles = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority()) // e.g., "ROLE_admin"
                .collect(Collectors.toSet());

        System.out.println("User roles: " + roles);

        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);

        return "profile.html"; // return the view name
    }
}
