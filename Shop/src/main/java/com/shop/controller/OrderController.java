package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrderController
{
    @GetMapping("/order-products")
    public String order()
    {
        return "/orders/order-products.html";
    }
}
