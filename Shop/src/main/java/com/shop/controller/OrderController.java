package com.shop.controller;

import com.shop.entity.*;
import com.shop.service.CartService;
import com.shop.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("order", order);

        return "/orders/order-products.html";
    }

    @GetMapping("/{orderId}/finalize")
    public String navToFinalize(@PathVariable long orderId, Model model)
    {
        List<CartItem> cartItems = cartService.getCartItemsByCartId(cartService.getCartIdByUserId(getUserId()));
        for(CartItem item: cartItems)
        {
            cartService.deleteCartItem(item);
        }
        model.addAttribute("shippingInfo", new ShippingInfo());
        return "/orders/order-finalize.html";
    }

    @PostMapping("/{orderId}/finalize")
    public String finalizeOrder(@PathVariable long orderId, @Valid @ModelAttribute
                                ShippingInfo shippingInfo, Model model, HttpServletRequest request)
    {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("_csrf", csrfToken);

        ShippingInfo newShippingInfo = new ShippingInfo();

        newShippingInfo.setOrderId(orderId);
        newShippingInfo.setFirstName(shippingInfo.getFirstName());
        newShippingInfo.setLastName(shippingInfo.getLastName());
        newShippingInfo.setPhoneNumber(shippingInfo.getPhoneNumber());
        newShippingInfo.setCountry(shippingInfo.getCountry());
        newShippingInfo.setCity(shippingInfo.getCity());
        newShippingInfo.setZipCode(shippingInfo.getZipCode());
        newShippingInfo.setAddress(shippingInfo.getAddress());

        orderService.saveShippingInfo(newShippingInfo);

        return "redirect:/orders/view";
    }

    @GetMapping("/view")
    public String viewOrders(Model model)
    {
        List<Order> orderList = orderService.getOrdersByUserId(getUserId());
        model.addAttribute("orders", orderList);

        return "orders/order-view.html";
    }

    @GetMapping("/{orderId}")
    public String viewOrder(@PathVariable long orderId, Model model)
    {
        List<OrderItem> orderItems = orderService.getOrderItemsByOrderId(orderId);
        System.out.println(orderItems);
        ShippingInfo shippingInfo = orderService.getShippingInfoByOrderId(orderId);
        System.out.println(shippingInfo);

        model.addAttribute("orderItemList", orderItems);
        model.addAttribute("shippingInfo", shippingInfo);

        return "/orders/order-info.html";
    }
}
