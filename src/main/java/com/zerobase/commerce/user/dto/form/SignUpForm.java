package com.zerobase.commerce.user.dto.form;

import com.zerobase.commerce.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class SignUpForm {
    @NotBlank(message = "id는 필수 입력 항목입니다.")
    @Length(min = 4, max = 16, message = "id는 4자 이상, 16자 이하로 입력해주세요.")
    private String userId;

    @NotBlank(message = "password는 필수 입력 항목입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotBlank(message = "checkedPassword는 필수 입력 항목입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String checkedPassword;

    @NotBlank(message = "username은 필수 입력 항목입니다.")
    private String username;

    public static User toEntity(SignUpForm form) {
        return User.builder()
                .userId(form.getUserId())
                .password(form.getPassword())
                .username(form.getUsername())
                .build();
    }
}
