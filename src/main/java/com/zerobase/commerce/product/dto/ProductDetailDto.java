package com.zerobase.commerce.product.dto;

import com.zerobase.commerce.product.entity.Product;
import com.zerobase.commerce.review.dto.ReviewDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDetailDto {
    private Long productId;
    private String name;
    private Long price;
    private String description;
    private Double averageRating;
    private Long reviewCount;
    private List<ReviewDto> reviews;

    public static ProductDetailDto fromEntity(Product product, List<ReviewDto> reviews) {
        return ProductDetailDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .averageRating(product.getAverageRating())
                .reviewCount(product.getReviewCount())
                .reviews(reviews)
                .build();
    }
}
