package com.shop.controller;

import com.shop.entity.Cart;
import com.shop.entity.Order;
import com.shop.entity.OrderItem;
import com.shop.entity.ShippingInfo;
import com.shop.service.CartService;
import com.shop.service.OrderService;
import jakarta.persistence.GeneratedValue;
import org.springframework.aot.hint.JavaSerializationHint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.shop.utils.UserUtils.getUserId;

@Controller
@RequestMapping("/orders")
public class OrderController
{
    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @GetMapping("/order-products")
    public String order(Model model)
    {
        Cart cart = cartService.getCartByUserId(getUserId());

        Order order = orderService.createOrder(cart);
        List<OrderItem> orderItems = orderService.createOrderItems(cart, order.getOrderId());

        model.addAttribute("items", orderItems);

        //add cart items deletion

        return "/orders/order-products.html";
    }

    @GetMapping("/finalize")
    public String navToFinalize(Model model)
    {
        model.addAttribute("shippingInfo", new ShippingInfo());

        return "/orders/order-finalize.html";
    }

    @PostMapping("/finalize")
    public String finalizeOrder(Model model)
    {

        return "test.html";
    }

}
