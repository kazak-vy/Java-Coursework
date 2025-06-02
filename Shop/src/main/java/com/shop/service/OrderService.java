package com.shop.service;

import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.Order;
import com.shop.entity.OrderItem;
import com.shop.repository.OrderItemRepository;
import com.shop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService
{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartService cartService;

    public Optional<Order> getOrderById(long orderId)
    {
        return orderRepository.findById(orderId);
    }

    public Optional<OrderItem> getOrderItemById(long orderItemId)
    {
        return orderItemRepository.findById(orderItemId);
    }

    public List<OrderItem> getOrderItemsById(List<Long> orderItemIds)
    {
        return orderItemRepository.findAllById(orderItemIds);
    }

    public void saveOrder(Order order)
    {
        orderRepository.save(order);
    }

    public void saveOrderItem(OrderItem orderItem)
    {
        orderItemRepository.save(orderItem);
    }

    public void deleteOrder(Order order)
    {
        orderRepository.delete(order);
    }

    public void deleteOrderItem(OrderItem orderItem)
    {
        orderItemRepository.delete(orderItem);
    }

    public Order createOrder(Cart cart)
    {
        Order newOrder = new Order();
        newOrder.setUserId(cart.getUserId());
        newOrder.setDate(LocalDateTime.now());
        newOrder.setPaymentStatus("pending");
        newOrder.setShippingStatus("pending");

        orderRepository.save(newOrder);

        return newOrder;
    }

    public List<OrderItem> createOrderItems(Cart cart, long orderId)
    {
        List<CartItem> cartItems = cartService.getCartItemsByCartId(cart.getCartId());
        List<OrderItem> orderItems = new ArrayList<>();
        for(CartItem item: cartItems)
        {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItems.add(orderItem);


            orderItemRepository.save(orderItem);
        }

        return orderItems;
    }
}

