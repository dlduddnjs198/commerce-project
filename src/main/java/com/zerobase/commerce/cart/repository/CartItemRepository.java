package com.zerobase.commerce.cart.repository;

import com.zerobase.commerce.cart.entity.CartItem;
import com.zerobase.commerce.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<List<CartItem>> findByUser(User user);

}
