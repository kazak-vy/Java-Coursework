package com.shop.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);

        return "profile.html";
    }
}
