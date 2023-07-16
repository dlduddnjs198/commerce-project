package com.zerobase.commerce.review.dto;

import com.zerobase.commerce.review.entity.Review;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ReviewDto {
    private Long id;
    private Double rating;
    private String content;
    private String createDate;
    private String userId;

    public static ReviewDto fromEntity(Review review) {
        LocalDateTime date = review.getCreateDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);

        String reviewUserId = review.getUser().getUserId();
        if (reviewUserId == null) {
            reviewUserId = "사용자";
        }

        return ReviewDto.builder()
                .id(review.getId())
                .rating(review.getRating())
                .content(review.getContent())
                .createDate(formattedDate)
                .userId(reviewUserId)
                .build();
    }

    public static List<ReviewDto> fromEntity(List<Review> reviews) {
        List<ReviewDto> reviewDtos = new ArrayList<>();
        for (Review r : reviews) {
            reviewDtos.add(ReviewDto.fromEntity(r));
        }
        return reviewDtos;
    }
}
