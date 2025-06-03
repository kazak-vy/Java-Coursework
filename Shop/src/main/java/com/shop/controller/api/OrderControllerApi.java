package com.shop.controller.api;

import com.shop.entity.*;
import com.shop.service.CartService;
import com.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.shop.utils.UserUtils.getUserId;

@RestController
@RequestMapping("/api-orders")
public class OrderControllerApi
{
    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @GetMapping("/order-products")
    public ResponseEntity<List<OrderItem>> order()
    {
        Cart cart = cartService.getCartByUserId(getUserId());

        Order order = orderService.createOrder(cart);
        List<OrderItem> orderItems = orderService.createOrderItems(cart, order.getOrderId());

        return ResponseEntity.ok(orderItems);
    }

    @PostMapping("/{orderId}/finalize")
    public ResponseEntity<ShippingInfo> finalizeOrder(@PathVariable long orderId, @RequestBody ShippingInfo shippingInfo)
    {
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

        return ResponseEntity.ok(newShippingInfo);
    }

    @GetMapping("/view")
    public ResponseEntity<List<Order>> viewOrders(Model model)
    {
        List<Order> orderList = orderService.getOrdersByUserId(getUserId());

        return ResponseEntity.ok(orderList);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<List<OrderItem>> viewOrder(@PathVariable long orderId, Model model)
    {
        List<OrderItem> orderItems = orderService.getOrderItemsByOrderId(orderId);

        return ResponseEntity.ok(orderItems);
    }
}
