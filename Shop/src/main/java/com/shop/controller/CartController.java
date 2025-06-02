package com.shop.controller;

import com.shop.dto.CartItemDTO;
import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.service.CartService;
import com.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.shop.utils.UserUtils.getUserId;


@Controller
@RequestMapping("/cart")
public class CartController
{
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping("/view")
    public String getCart(Model model)
    {
        if(cartService.getCartByUserId(getUserId()) == null)
        {
            System.out.println("new cart");
            Cart newCart = new Cart();
            newCart.setUserId(getUserId());

            cartService.saveCart(newCart);
        }
        else
        {
            System.out.println("existing cart");
            List<CartItem> cartItemsList = cartService.getCartItemsByUserId(getUserId());
            model.addAttribute("itemsList", cartItemsList);

            List<CartItemDTO> dtoList = (cartService.getCartItemDTOs(cartItemsList));
            model.addAttribute("dtoList", dtoList);
        }
        return "cart/cart-view.html";
    }

    @GetMapping("/{productId}/add")
    public String addToCart(@PathVariable long productId)
    {
        long cartId = cartService.getCartIdByUserId(getUserId());
        cartService.addToCart(cartId, productId);

        return "redirect:/products/product-list";
    }

    @DeleteMapping("/remove/{productId}")
    public String removeFromCart(@PathVariable Long productId, Authentication authentication) {
       return "test.html";
    }
}
