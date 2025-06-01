package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController
{
    @GetMapping("/")
    public String index()
    {
        return "index.html";
    }

    @GetMapping("/authorized")
    public String loggedIn()
    {
        return "test.html";
    }

}
