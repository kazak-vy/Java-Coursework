package com.shop.controller.api;

import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.service.CartService;
import com.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.shop.utils.UserUtils.getUserId;

@RestController
@RequestMapping("/api-carts")
public class CartControllerApi
{    @Autowired
private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping("/view")
    public ResponseEntity<?> getCart()
    {
        if(cartService.getCartByUserId(getUserId()) == null)
        {
            Cart newCart = new Cart();
            newCart.setUserId(getUserId());

            cartService.saveCart(newCart);

            return ResponseEntity.ok(newCart);
        }
        else
        {
            return ResponseEntity.ok(cartService.getCartItemsByUserId(getUserId()));
        }
    }

    @PutMapping("/{productId}/add")
    public ResponseEntity<?> addToCart(@PathVariable long productId)
    {
        if(cartService.getCartByUserId(getUserId()) == null)
        {
            Cart newCart = new Cart();
            newCart.setUserId(getUserId());

            cartService.saveCart(newCart);
            return ResponseEntity.ok(newCart);
        }
        long cartId = cartService.getCartIdByUserId(getUserId());

        cartService.addToCart(cartId, productId);
        return ResponseEntity.ok(cartService.getCartItemsByUserId(getUserId()));

    }

    @DeleteMapping("/{productId}/remove")
    public ResponseEntity<List<CartItem>> removeFromCart(@PathVariable Long productId)
    {
        long cartId = cartService.getCartIdByUserId(getUserId());
        cartService.deleteCartItem(cartService.getCartItemByCartIdAndProductId(cartId, productId));

        return ResponseEntity.ok(cartService.getCartItemsByUserId(getUserId()));
    }

    @PutMapping("/{productId}/remove-one")
    public ResponseEntity<List<CartItem>> removeOneFromCart(@PathVariable Long productId)
    {
        long cartId = cartService.getCartIdByUserId(getUserId());
        CartItem updatedCartItem = cartService.getCartItemByCartIdAndProductId(cartId, productId);
        if(updatedCartItem.getQuantity() == 1)
        {
            cartService.deleteCartItem(updatedCartItem);
        }
        else
        {
            updatedCartItem.setQuantity(updatedCartItem.getQuantity() - 1);
            cartService.saveCartItem(updatedCartItem);
        }

        return ResponseEntity.ok(cartService.getCartItemsByUserId(getUserId()));
    }

    @PutMapping("/{productId}/add-one")
    public ResponseEntity<List<CartItem>> addOneToCart(@PathVariable Long productId)
    {
        long cartId = cartService.getCartIdByUserId(getUserId());
        cartService.addToCart(cartId, productId);

        return ResponseEntity.ok(cartService.getCartItemsByUserId(getUserId()));
    }
}
