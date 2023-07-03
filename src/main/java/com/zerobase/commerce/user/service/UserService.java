package com.zerobase.commerce.user.service;

import com.zerobase.commerce.user.dto.UserResponseDto;
import com.zerobase.commerce.user.dto.form.SignInForm;
import com.zerobase.commerce.user.dto.form.SignUpForm;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    /**
     * 회원가입
     */
    UserResponseDto signUp(SignUpForm form);

    /**
     * 로그인
     */
    String signIn(SignInForm form);

}
