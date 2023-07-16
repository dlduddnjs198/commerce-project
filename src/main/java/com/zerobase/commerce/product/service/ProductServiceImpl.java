package com.zerobase.commerce.product.service;

import com.zerobase.commerce.exception.ProductException;
import com.zerobase.commerce.product.dto.ProductDetailDto;
import com.zerobase.commerce.product.dto.ProductDto;
import com.zerobase.commerce.product.entity.Product;
import com.zerobase.commerce.product.repository.ProductRepository;
import com.zerobase.commerce.review.dto.ReviewDto;
import com.zerobase.commerce.review.entity.Review;
import com.zerobase.commerce.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.zerobase.commerce.type.ErrorCode.INVALID_REQUEST;
import static com.zerobase.commerce.type.ErrorCode.PRODUCT_NOT_FOUND;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public List<ProductDto> searchProducts(String keyword, int page, int size, String sort) {
        Pageable pageable = createPageable(page, size, sort, true);
        List<ProductDto> products = new ArrayList<>();
        Page<Product> pageProducts = productRepository.findByPartialMatchedName(keyword, pageable);
        if (!pageProducts.isEmpty()) {
            products = ProductDto.fromEntity(pageProducts.getContent());
        }
        return products;
    }

    @Override
    public List<ProductDto> getSortedProducts(int page, int size, String sort) {

        Pageable pageable = createPageable(page, size, sort, false);

        Page<Product> pageProducts = productRepository.findAll(pageable);
        List<ProductDto> products = ProductDto.fromEntity(pageProducts.getContent());

        return products;
    }

    @Override
    public ProductDetailDto getProductDetail(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND));

        List<ReviewDto> reviews = new ArrayList<>();

        Pageable pageable = PageRequest.of(0, 10);

        Page<Review> pageReviews = reviewRepository.findByProduct(product, pageable);
        if (!pageReviews.isEmpty()) {
            reviews = ReviewDto.fromEntity(pageReviews.getContent());
        }

        return ProductDetailDto.fromEntity(product, reviews);
    }

    private Pageable createPageable(int page, int size, String sort, boolean search) {

        Sort sorting;
        Pageable pageable;

        if (sort != null) {
            if (sort.equals("priceAsce")) {
                sorting = Sort.by("price").ascending();
            } else if (sort.equals("price")) {
                sorting = Sort.by("price").descending();
            } else if (sort.equals("averageRating")) {
                sorting = search ? Sort.by("average_rating").descending() : Sort.by("averageRating").descending();
            } else if (sort.equals("reviewCount")) {
                sorting = search ? Sort.by("review_count").descending() : Sort.by("reviewCount").descending();
            } else {
                throw new ProductException(INVALID_REQUEST);
            }
            pageable = PageRequest.of(page, size, sorting);
        } else { // 기본으로 최신순 정렬
            sorting = search ? Sort.by("create_date").descending() : Sort.by("createDate").descending();
            pageable = PageRequest.of(page, size, sorting);
        }

        return pageable;
    }
}
