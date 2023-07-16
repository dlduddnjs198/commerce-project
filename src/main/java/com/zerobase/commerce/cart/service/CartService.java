package com.zerobase.commerce.cart.service;

import com.zerobase.commerce.cart.dto.CartItemDto;

import java.util.List;

public interface CartService {
    CartItemDto addToCart(String userId, Long productId, int quantity);

    List<CartItemDto> getCartItems(String userId);

    CartItemDto removeFromCart(String userId, String productName);
}
