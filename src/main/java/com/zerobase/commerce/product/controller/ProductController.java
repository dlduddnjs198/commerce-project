package com.zerobase.commerce.product.controller;

import com.zerobase.commerce.product.dto.ProductDetailDto;
import com.zerobase.commerce.product.dto.ProductDto;
import com.zerobase.commerce.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    // 사용자에 의해 동적으로 검색어가 결정되므로 유효성 검사는 불필요하다고 판단
    // page, size를 통해 페이지 처리를 진행(default page = 0, default size = 10)
    // 정렬이 설정된다면 해당 정렬로 반환
    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam("keyword") String keyword
            , @RequestParam(value = "page", defaultValue = "0") int page
            , @RequestParam(value = "size", defaultValue = "10") int size
            , @RequestParam(value = "sort", required = false) String sort) {
        List<ProductDto> products = productService.searchProducts(keyword, page, size, sort);
        return ResponseEntity.ok(products);
    }

    // 페이지를 정렬된 형태로 조회
    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getSortedProducts(
            @RequestParam(value = "page", defaultValue = "0") int page
            , @RequestParam(value = "size", defaultValue = "10") int size
            , @RequestParam(value = "sort", required = false) String sort) {
        List<ProductDto> products = productService.getSortedProducts(page, size, sort);
        return ResponseEntity.ok(products);
    }


    // 상품 상세 페이지 정보 보여주기
    // 리뷰는 default로 페이징 처리, 정렬 및 다음 페이지 리뷰를 받으려면 따로 API를 호출해야한다.
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailDto> getProductDetails(@PathVariable Long productId) {
        ProductDetailDto productDetails = productService.getProductDetail(productId);
        return ResponseEntity.ok(productDetails);
    }

}
