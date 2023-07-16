package com.zerobase.commerce.review.service;

import com.zerobase.commerce.review.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    // 해당 페이지의 리뷰 조회
    List<ReviewDto> getReviews(Long productId, int page, int size, String sort);

    ReviewDto createReview(Long productId, String userId, String content, Double rating);

    ReviewDto deleteReview(Long productId, Long reviewId);
}
