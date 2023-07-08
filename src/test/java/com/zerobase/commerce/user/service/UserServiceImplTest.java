package com.zerobase.commerce.user.service;

import com.zerobase.commerce.user.entity.User;
import com.zerobase.commerce.exception.UserException;
import com.zerobase.commerce.user.dto.form.SignInForm;
import com.zerobase.commerce.user.dto.form.SignUpForm;
import com.zerobase.commerce.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.zerobase.commerce.type.ErrorCode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @DisplayName("ID가 이미 존재하는 경우 테스트")
    @Test
    public void signUpServiceTest() {
        //given(어떤 데이터가 있을때)
        String existingUserId = "zerobase123";
        SignUpForm form = SignUpForm.builder()
                .userId(existingUserId)
                .build();

        User existingUser = new User();
        existingUser.setUserId(existingUserId);

        //when(어떤 동작을 하게되면)
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(existingUser));

        //then(어떤 결과가 나와야한다)
        UserException e = assertThrows(UserException.class, () -> userService.signUp(form));
        assertEquals(USERID_ALREADY_EXIST, e.getErrorCode());
    }

    @DisplayName("해당하는 로그인 ID가 없는 경우 테스트")
    @Test
    void signInServiceTest1() {
        //given(어떤 데이터가 있을때)
        SignInForm form = SignInForm.builder()
                .userId("onebase123")
                .password("qwerty!23")
                .build();

        //when(어떤 동작을 하게되면)
        when(userRepository.findByUserId(form.getUserId())).thenReturn(Optional.empty());

        //then(어떤 결과가 나와야한다)
        UserException e = assertThrows(UserException.class, () -> userService.signIn(form));
        assertEquals(USER_NOT_FOUND, e.getErrorCode());
    }

    @DisplayName("로그인 패스워드가 없는 경우 테스트")
    @Test
    void signInServiceTest2() {
        //given(어떤 데이터가 있을때)
        SignInForm form = SignInForm.builder()
                .userId("zerobase123")
                .password("qwerty!68")
                .build();

        User user = User.builder()
                .userId("zerobase123")
                .password("$2a$10$Q5wbv/PLfG.Abpx1QSDtOuVtLXKbB3m8BYt0xt/6mv.zDCvSlq74i") // qwerty!23
                .username("홍길동")
                .id(2L)
                .build();

        //when(어떤 동작을 하게되면)
        when(userRepository.findByUserId(form.getUserId())).thenReturn(Optional.of(user));

        //then(어떤 결과가 나와야한다)
        UserException e = assertThrows(UserException.class, () -> userService.signIn(form));
        assertEquals(PASSWORD_UNMATCHED, e.getErrorCode());
    }
}