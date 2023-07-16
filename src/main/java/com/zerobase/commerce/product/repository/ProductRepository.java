package com.zerobase.commerce.product.repository;

import com.zerobase.commerce.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // MySQL FULLTEXT 인덱스와 함께 MATCH AGAINST 쿼리를 사용하여 검색 결과를 내림차순 정렬하여 이를 통해 검색 후 일치도가 높은 순으로 정렬
    //  Full-Text 검색과 같은 복잡한 쿼리의 경우, COUNT()를 사용하는 것보다 MATCH AGAINST와 같은 검색 조건을 포함한
    //  countQuery를 사용하는 것이 더 정확한 결과를 제공할 수 있다고 한다.
    //  ORDER BY MATCH(p.name) AGAINST(:keyword IN BOOLEAN MODE) DESC
    @Query(value = "SELECT * FROM Product p WHERE MATCH(p.name) AGAINST(:keyword)",
//            countQuery = "SELECT COUNT(*) FROM Product p WHERE MATCH(p.name) AGAINST(:keyword IN BOOLEAN MODE)",
            nativeQuery = true)
    Page<Product> findByPartialMatchedName(@Param("keyword") String keyword, Pageable pageable);

    // 완전일치검색을 위한 메소드
    Optional<List<Product>> findByName(String name);

    Page<Product> findAll(Pageable pageable);
}
