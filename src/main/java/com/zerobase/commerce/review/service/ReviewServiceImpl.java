package com.zerobase.commerce.review.service;

import com.zerobase.commerce.exception.ProductException;
import com.zerobase.commerce.exception.ReviewException;
import com.zerobase.commerce.exception.UserException;
import com.zerobase.commerce.product.entity.Product;
import com.zerobase.commerce.product.repository.ProductRepository;
import com.zerobase.commerce.review.dto.ReviewDto;
import com.zerobase.commerce.review.entity.Review;
import com.zerobase.commerce.review.repository.ReviewRepository;
import com.zerobase.commerce.user.entity.User;
import com.zerobase.commerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.zerobase.commerce.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDto> getReviews(Long productId, int page, int size, String sort) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND));

        List<ReviewDto> reviews = new ArrayList<>();

        Sort sorting = Sort.by("createDate").descending();
        Pageable pageable = PageRequest.of(page, size, sorting);

        Page<Review> pageReviews = reviewRepository.findByProduct(product, pageable);
        if (!pageReviews.isEmpty()) {
            reviews = ReviewDto.fromEntity(pageReviews.getContent());
        }

        return reviews;
    }

    @Transactional
    @Override
    public ReviewDto createReview(Long productId, String userId, String content, Double rating) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND));
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));
        Review review = Review.builder()
                .product(product)
                .rating(rating)
                .content(content)
                .user(user)
                .build();

        double totalRating = product.getReviewCount() * product.getAverageRating() + rating;
        Long totalNumberOfRatings = product.getReviewCount() + 1;
        double newAverageRating = totalRating / totalNumberOfRatings;

        // product update
        Product.builder()
                .reviewCount(totalNumberOfRatings)
                .averageRating(newAverageRating)
                .build();

        return ReviewDto.fromEntity(reviewRepository.save(review));
    }

    @Transactional
    @Override
    public ReviewDto deleteReview(Long productId, Long reviewId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(PRODUCT_NOT_FOUND));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(REVIEW_NOT_FOUND));
        reviewRepository.delete(review);
        return ReviewDto.fromEntity(review);
    }

    private Pageable createPageable(int page, int size, String sort) {

        Sort sorting;
        Pageable pageable;

        if (sort != null) {
            if (sort.equals("priceAsce")) {
                sorting = Sort.by("price").ascending();
            } else if (sort.equals("price")) {
                sorting = Sort.by("price").descending();
            } else if (sort.equals("averageRating")) {
                sorting = Sort.by("average_rating").descending();
            } else if (sort.equals("reviewCount")) {
                sorting = Sort.by("review_count").descending();
            } else {
                throw new ProductException(INVALID_REQUEST);
            }
            pageable = PageRequest.of(page, size, sorting);
        } else { // 기본으로 최신순 정렬
            sorting = Sort.by("createDate").descending();
            pageable = PageRequest.of(page, size, sorting);
        }

        return pageable;
    }
}
