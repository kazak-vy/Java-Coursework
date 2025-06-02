package com.shop.service;

import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.repository.CartItemRepository;
import com.shop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService
{
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<Cart> getAllCarts()
    {
        return cartRepository.findAll();
    }

    public Optional<Cart> getCartById(long cartId)
    {
        return cartRepository.findById(cartId);
    }

    public void saveCart(Cart cart)
    {
        cartRepository.save(cart);
    }

    public void deleteCart(Cart cart)
    {
        cartRepository.delete(cart);
    }

    public Cart getCartByUserId(String userId)
    {
        return cartRepository.findCartByUserId(userId);
    }

    public long getCartIdByUserId(String userId)
    {
        return cartRepository.findCartByUserId(userId).getCartId();
    }

    public List<CartItem> getAllCartItems()
    {
        return cartItemRepository.findAll();
    }

    public Optional<CartItem> getCartItemById(long cartItemId)
    {
        return cartItemRepository.findById(cartItemId);
    }

    public void saveCartItem(CartItem cartItem)
    {
        cartItemRepository.save(cartItem);
    }

    public void deleteCartItem(CartItem cartItem)
    {
        cartItemRepository.delete(cartItem);
    }

    public List<CartItem> getCartItemsByCartId(long cartId)
    {
        return cartItemRepository.findCartItemsByCartId(cartId);
    }

    public List<CartItem> getCartItemsByUserId(String userId)
    {
        long cartId = cartRepository.findCartByUserId(userId).getCartId();

        return cartItemRepository.findCartItemsByCartId(cartId);
    }

    public void addToCart(long cartId, long productId)
    {
        Optional<CartItem> existingItem = cartItemRepository.findCartItemByCartIdAndProductId(cartId, productId);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + 1);
            cartItemRepository.save(item);
        } else {
            // Add new product to cart
            CartItem newItem = new CartItem();
            newItem.setCartId(cartId);
            newItem.setProductId(productId);
            newItem.setQuantity(1);
            cartItemRepository.save(newItem);
        }
    }
}
