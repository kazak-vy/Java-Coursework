package com.shop.repository;

import com.shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long>
{
    public List<CartItem> findCartItemsByCartId(long cartId);
    Optional<CartItem> findCartItemByCartIdAndProductId(long cartId, long productId);
}
