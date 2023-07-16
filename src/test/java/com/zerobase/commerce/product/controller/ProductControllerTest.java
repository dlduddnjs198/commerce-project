package com.zerobase.commerce.product.controller;

import com.zerobase.commerce.product.repository.ProductRepository;
import com.zerobase.commerce.review.repository.ReviewRepository;
import com.zerobase.commerce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@Transactional
class ProductControllerTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

//    @Test
//    void makeRandomProductAndReview(){
//        Random random = new Random();
//        String[] productnames = new String[] {"자료구조 ", "알고리즘 ", "제로베이스 ", "맛있는 짜파게티 ", "무서운 공포명화 ", "무서운 할머니 "
//        , "게임은 재밌다 ", "게임이 하고싶다고 말해 ", "당신은 누구길래 그러십니까? ", "뿌요뿌요 테트리스 "};
//        for (int i = 1; i <= 1000000; i++) {
//            String productName = productnames[i/100000] + i;
//            double rating = Math.round(random.nextDouble() * 5 * 10) / 10.0; // 0.0부터 5.0까지의 랜덤 double 값 (소숫점 첫째자리까지)
//            int reviewCount = random.nextInt(100) + 1; // 1부터 100까지의 랜덤 int 값
//            long price = i * 1000; // 숫자 하나씩 증가하는 가격
//            String description = "Description for " + productName;
//            productRepository.save(Product.builder()
//                    .name(productName)
//                    .averageRating(rating)
//                    .price(price)
//                    .reviewCount((long) reviewCount)
//                    .description(description)
//                    .build());
//        }
//    }

}