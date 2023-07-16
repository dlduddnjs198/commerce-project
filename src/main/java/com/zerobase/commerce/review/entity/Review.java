package com.zerobase.commerce.review.entity;

import com.zerobase.commerce.product.entity.Product;
import com.zerobase.commerce.user.entity.BaseTimeEntity;
import com.zerobase.commerce.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @Column
    private Double rating;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
