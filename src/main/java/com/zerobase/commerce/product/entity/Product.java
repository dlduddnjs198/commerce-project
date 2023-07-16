package com.zerobase.commerce.product.entity;

import com.zerobase.commerce.user.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // MySQL Full-Text 인덱스 설정을 통해 부분일치검색을 용이하게 하려고 설정
    @Column(nullable = false, columnDefinition = "VARCHAR(255) NOT NULL, FULLTEXT INDEX idx_name (name)")
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double averageRating;

    @Column(nullable = false)
    private Long reviewCount;
}
