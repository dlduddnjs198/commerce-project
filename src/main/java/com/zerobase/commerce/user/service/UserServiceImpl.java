package com.zerobase.commerce.user.service;

import com.zerobase.commerce.User;
import com.zerobase.commerce.configuration.TokenProvider;
import com.zerobase.commerce.exception.UserException;
import com.zerobase.commerce.type.Role;
import com.zerobase.commerce.user.dto.UserResponseDto;
import com.zerobase.commerce.user.dto.form.SignInForm;
import com.zerobase.commerce.user.dto.form.SignUpForm;
import com.zerobase.commerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.zerobase.commerce.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserResponseDto signUp(SignUpForm form) {
//        User user1 = userRepository.findByUserId(form.getUserId())
//                .orElseThrow(() -> new UserException(USERID_ALREADY_EXIST));
        if (userRepository.findByUserId(form.getUserId()).isPresent()) {
            throw new UserException(USERID_ALREADY_EXIST);
        }

        String encPassword = BCrypt.hashpw(form.getPassword(), BCrypt.gensalt());
        form.setPassword(encPassword);

        User user = userRepository.save(SignUpForm.toEntity(form));

        return UserResponseDto.builder()
                .userId(user.getUserId())
                .build();
    }

    @Transactional
    @Override
    public String signIn(SignInForm form) {
        User user = userRepository.findByUserId(form.getUserId()).orElseThrow(() -> new UserException(USER_NOT_FOUND));

        if (!BCrypt.checkpw(form.getPassword(), user.getPassword())) {
            throw new UserException(PASSWORD_UNMATCHED);
        }

        // token을 어디에 저장할지 설정하지 않으면 default로 쿠키에 저장된다고 한다.
        return TokenProvider.generateToken(form.getUserId(), Role.USER.name());
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(USER_NOT_FOUND));

        // role, 해당 회원의 역할, 해당 계정은 어느 권한까지 존재하는지, 단지 회원인지, 관리자인지 그걸 표현한 리스트가 UserDetails에 필요하다.
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.name()));

        // 관리자 권한이 있는 계정인 경우 추가

        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(), grantedAuthorities);
    }
}
