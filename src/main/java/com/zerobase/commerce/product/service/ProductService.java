package com.zerobase.commerce.product.service;

import com.zerobase.commerce.product.dto.ProductDetailDto;
import com.zerobase.commerce.product.dto.ProductDto;

import java.util.List;

public interface ProductService {

    // 상품 검색
    List<ProductDto> searchProducts(String keyword, int page, int size, String sort);
    // 상품 조회(default:최신순)
    List<ProductDto> getSortedProducts(int page, int size, String sort);
    // 상품 상세정보 조회
    ProductDetailDto getProductDetail(Long productId);
}
