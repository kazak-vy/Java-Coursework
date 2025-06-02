package com.shop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "shipping_status")
    private String shippingStatus;

}
