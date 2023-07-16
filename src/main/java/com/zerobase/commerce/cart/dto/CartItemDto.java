package com.zerobase.commerce.cart.dto;

import com.zerobase.commerce.cart.entity.CartItem;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CartItemDto {

    private String userId;
    private String productName;
    private Long quantity;

    public static CartItemDto fromEntity(CartItem cartItem) {
        return CartItemDto.builder()
                .userId(cartItem.getUser().getUserId())
                .productName(cartItem.getProduct().getName())
                .quantity(cartItem.getQuantity())
                .build();
    }

    public static List<CartItemDto> fromEntity(List<CartItem> cartItems) {
        List<CartItemDto> cartItemDtos = new ArrayList<>();
        for (CartItem c : cartItems) {
            cartItemDtos.add(CartItemDto.fromEntity(c));
        }
        return cartItemDtos;
    }
}
