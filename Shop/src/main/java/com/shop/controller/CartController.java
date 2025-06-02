package com.shop.controller;

import com.shop.dto.CartItemDTO;
import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.service.CartService;
import com.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            Cart newCart = new Cart();
            newCart.setUserId(getUserId());

            cartService.saveCart(newCart);
        }
        else
        {
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

    @GetMapping("/{productId}/remove")
    public String removeFromCart(@PathVariable Long productId)
    {
        long cartId = cartService.getCartIdByUserId(getUserId());
        cartService.deleteCartItem(cartService.getCartItemByCartIdAndProductId(cartId, productId));
       return "redirect:/cart/view";
    }

    @GetMapping("/{productId}/remove-one")
    public String removeOneFromCart(@PathVariable Long productId)
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

        return "redirect:/cart/view";
    }

    @GetMapping("/{productId}/add-one")
    public String addOneToCart(@PathVariable Long productId)
    {
        long cartId = cartService.getCartIdByUserId(getUserId());
        cartService.addToCart(cartId, productId);

        return "redirect:/cart/view";
    }
}
