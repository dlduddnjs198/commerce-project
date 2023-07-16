package com.zerobase.commerce.product.dto;

import com.zerobase.commerce.product.entity.Product;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDto {

    private Long productId;
    private String name;
    private Long price;
    private Double averageRating;
    private Long reviewCount;

    public static ProductDto fromEntity(Product product) {
        return ProductDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .averageRating(product.getAverageRating())
                .reviewCount(product.getReviewCount())
                .build();
    }

    public static List<ProductDto> fromEntity(List<Product> products) {
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product p : products) {
            productDtos.add(ProductDto.fromEntity(p));
        }
        return productDtos;
    }

}
