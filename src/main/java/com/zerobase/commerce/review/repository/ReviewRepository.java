package com.zerobase.commerce.review.repository;

import com.zerobase.commerce.product.entity.Product;
import com.zerobase.commerce.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByProduct(Product product, Pageable pageable);
}
