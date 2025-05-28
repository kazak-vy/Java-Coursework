package com.shop.controller;

import com.shop.model.Product;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class HomeController
{
    @GetMapping("/")
    public String index()
    {
        return "index.html";
    }



}
