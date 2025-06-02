package com.shop.controller;

import com.shop.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
