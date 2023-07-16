package com.zerobase.commerce.cart.service;

import com.zerobase.commerce.cart.dto.CartItemDto;
import com.zerobase.commerce.cart.entity.CartItem;
import com.zerobase.commerce.cart.repository.CartItemRepository;
import com.zerobase.commerce.exception.ProductException;
import com.zerobase.commerce.exception.UserException;
import com.zerobase.commerce.product.entity.Product;
import com.zerobase.commerce.product.repository.ProductRepository;
import com.zerobase.commerce.type.ErrorCode;
import com.zerobase.commerce.user.entity.User;
import com.zerobase.commerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;


    @Transactional
    @Override
    public CartItemDto addToCart(String userId, Long productId, int quantity) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND));
        boolean existingCartItem = false;
        CartItem item = null;

        // 상품이 이미 장바구니에 있는지 확인
        List<CartItem> existingCartItems = cartItemRepository.findByUser(user)
                .orElse(null);
        if (existingCartItems != null) {
            for (CartItem cartItem : existingCartItems) {
                if (cartItem.getProduct().getId().equals(productId)) {
                    existingCartItem = true;
                    item = cartItem;
                    break;
                }
            }
        }

        if (existingCartItem) {
            // 이미 장바구니에 있는 경우 수량 업데이트
            CartItem.builder().quantity(item.getQuantity() + quantity).build();
        } else {
            // 장바구니에 새로운 상품 추가
            item = CartItem.builder()
                    .product(product)
                    .user(user)
                    .quantity((long) quantity)
                    .build();
        }

        cartItemRepository.save(item);
        return CartItemDto.fromEntity(item);
    }

    @Override
    public List<CartItemDto> getCartItems(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
        List<CartItem> cartItemList = cartItemRepository.findByUser(user)
                .orElse(new ArrayList<>());
        return CartItemDto.fromEntity(cartItemList);
    }

    @Transactional
    @Override
    public CartItemDto removeFromCart(String userId, String productName) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));
        List<CartItem> cartItems = cartItemRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저의 장바구니 내역을 찾을 수 없습니다."));
        boolean existProduct = false;
        CartItem item = new CartItem();
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getName().equals(productName)) {
                item = cartItem;
                cartItemRepository.delete(cartItem);
                existProduct = true;
                break;
            }
        }
        return CartItemDto.fromEntity(item);
    }
}
