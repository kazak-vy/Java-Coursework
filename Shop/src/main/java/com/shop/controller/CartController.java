package com.shop.controller;

import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static com.shop.utils.UserUtils.getUserId;


@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public Cart getCart(OAuth2AuthenticationToken authentication)
    {
        return cartService.getCart(getUserId());
    }

    @PostMapping("/add")
    public Cart addToCart(@RequestBody CartItem item, Authentication authentication) {
        String userId = "test";
        return cartService.addItem(userId, item);
    }

    @DeleteMapping("/remove/{productId}")
    public Cart removeFromCart(@PathVariable Long productId, Authentication authentication) {
        String userId = "test";
        return cartService.removeItem(userId, productId);
    }
}
