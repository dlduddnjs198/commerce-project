package com.zerobase.commerce.user.service;

import com.zerobase.commerce.Role;
import com.zerobase.commerce.User;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public String signUp(SignUpForm form) {

        if (userRepository.findByUserId(form.getUserId()).isPresent()) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        if (!form.getPassword().equals(form.getCheckedPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String encPassword = BCrypt.hashpw(form.getPassword(), BCrypt.gensalt());
        form.setPassword(encPassword);

        User user = userRepository.save(SignUpForm.toEntity(form));

        return "회원가입이 완료되었습니다.";
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        Optional<User> optionalUser = userRepository.findByUserId(userId);
        // 회원정보가 없는 경우
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        // role, 해당 회원의 역할, 해당 계정은 어느 권한까지 존재하는지, 단지 회원인지, 관리자인지 그걸 표현한 리스트가 UserDetails에 필요하다.
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.name()));

        // 관리자 권한이 있는 계정인 경우 추가

        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPassword(), grantedAuthorities);
    }
}
