package com.shop.service;

import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CartService {

    private final Map<String, Cart> carts = new ConcurrentHashMap<>();

    public Cart getCart(String userId) {
        return carts.computeIfAbsent(userId, k -> new Cart(userId, new ArrayList<>()));
    }

    public Cart addItem(String userId, CartItem item) {
        Cart cart = getCart(userId);
        cart.getItems().add(item); // enhance: merge if same product
        return cart;
    }

    public Cart removeItem(String userId, Long productId) {
        Cart cart = getCart(userId);
        cart.getItems().removeIf(i -> i.getProductId().equals(productId));
        return cart;
    }
}
