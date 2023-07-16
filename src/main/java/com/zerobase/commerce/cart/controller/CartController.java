package com.zerobase.commerce.cart.controller;

import com.zerobase.commerce.cart.dto.CartItemDto;
import com.zerobase.commerce.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartItemDto> addToCart(@RequestParam String userId,
                                                 @RequestParam Long productId,
                                                 @RequestParam int quantity) {
        CartItemDto cartItem = cartService.addToCart(userId, productId, quantity);
        return ResponseEntity.ok(cartItem);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemDto>> getCartItems(@PathVariable String userId) {
        List<CartItemDto> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }

    @DeleteMapping("/{userId}/{productName}")
    public ResponseEntity<CartItemDto> removeFromCart(@PathVariable String userId,
                                                      @PathVariable String productName) {
        CartItemDto cartItem = cartService.removeFromCart(userId, productName);
        return ResponseEntity.ok(cartItem);
    }
}