package com.zerobase.commerce.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserResponseDto {
    private String userId;
    private String password;
    private String username;
}
