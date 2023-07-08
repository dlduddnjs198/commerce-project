package com.zerobase.commerce.user.dto.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class SignInForm {
    @NotBlank(message = "id는 필수 입력 항목입니다.")
    @Length(min = 4, max = 16, message = "id는 4자 이상, 16자 이하로 입력해주세요.")
    private String userId;

    @NotBlank(message = "password는 필수 입력 항목입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;
}