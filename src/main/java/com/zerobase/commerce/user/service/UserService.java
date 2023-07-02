package com.zerobase.commerce.user.service;

import com.zerobase.commerce.user.dto.form.SignUpForm;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    /**
     * 회원가입
     */
    String signUp(SignUpForm form);

}
