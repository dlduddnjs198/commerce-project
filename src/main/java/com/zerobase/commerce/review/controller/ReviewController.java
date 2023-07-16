package com.zerobase.commerce.review.controller;

import com.zerobase.commerce.review.dto.ReviewDto;
import com.zerobase.commerce.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 페이지를 정렬된 형태로 조회
    @GetMapping("/{productId}")
    public ResponseEntity<List<ReviewDto>> getSortedReviews(
            @PathVariable Long productId
            , @RequestParam(value = "page", defaultValue = "0") int page
            , @RequestParam(value = "size", defaultValue = "10") int size
            , @RequestParam(value = "sort", required = false) String sort) {
        List<ReviewDto> reviews = reviewService.getReviews(productId, page, size, sort);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<ReviewDto> createReview(
            @PathVariable Long productId,
            @RequestParam String userId,
            @RequestParam String content,
            @RequestParam Double rating) {
        ReviewDto review = reviewService.createReview(productId, userId, content, rating);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteReview(
            @PathVariable Long productId,
            @RequestParam Long reviewId
    ) {
        reviewService.deleteReview(productId, reviewId);
        return ResponseEntity.ok("Review deleted successfully");
    }
}
