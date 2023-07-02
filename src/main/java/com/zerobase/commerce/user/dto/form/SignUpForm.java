package com.zerobase.commerce.user.dto.form;

import com.zerobase.commerce.User;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class SignUpForm {
    private String userId;
    private String password;
    private String checkedPassword;
    private String username;

    public static User toEntity(SignUpForm form) {
        return User.builder()
                .userId(form.getUserId())
                .password(form.getPassword())
                .username(form.getUsername())
                .build();
    }
}
