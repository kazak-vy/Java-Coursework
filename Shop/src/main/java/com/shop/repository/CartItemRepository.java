package com.shop.repository;

import com.shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>
{
    List<CartItem> findCartItemsByCartId(long cartId);
    Optional<CartItem> findCartItemByCartIdAndProductId(long cartId, long productId);
    Optional<CartItem> findCartItemByProductId(long productId);
}
