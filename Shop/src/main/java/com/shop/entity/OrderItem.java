package com.shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "order_items",
        uniqueConstraints = @UniqueConstraint(columnNames = {"order_id", "order_item_id"})
        )
public class OrderItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderItemId;

    @Column(name = "order_id", nullable = false)
    private long orderId;

    @Column(name = "product_id", nullable = false)
    private long productId;

    @Column(name = "seller_id")
    private String sellerId;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
