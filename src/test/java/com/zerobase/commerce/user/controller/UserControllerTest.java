package com.zerobase.commerce.user.controller;

import com.zerobase.commerce.user.entity.User;
import com.zerobase.commerce.configuration.TokenProvider;
import com.zerobase.commerce.dto.ErrorResponse;
import com.zerobase.commerce.service.TokenService;
import com.zerobase.commerce.type.ErrorCode;
import com.zerobase.commerce.user.dto.UserResponseDto;
import com.zerobase.commerce.user.dto.form.SignInForm;
import com.zerobase.commerce.user.dto.form.SignUpForm;
import com.zerobase.commerce.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class UserControllerTest {


    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Autowired
    private UserController userController;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private TokenService tokenService;

    @Mock
    private UserRepository userRepository;

    @DisplayName("유효성 검사 오류 테스트")
    @Test
    void signUpControllerTest1() {
        //given(어떤 데이터가 있을때)
        SignUpForm form = SignUpForm.builder()
                .userId("zerobase123")
                .password("qwerty123")
                .checkedPassword("qwerty123")
                .username("홍길동")
                .build();
        //when(어떤 동작을 하게되면)
        Set<ConstraintViolation<SignUpForm>> violations = validator.validate(form); // 유효하지 않은 경우 violations 값을 가지고 있다.
        //then(어떤 결과가 나와야한다)
        assertFalse(violations.isEmpty());
        violations
                .forEach(error -> {
                    assertEquals("비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.", error.getMessage());
                });
    }

    @DisplayName("비밀번호 불일치 오류 테스트")
    @Test
    void signUpControllerTest2() {
        //given(어떤 데이터가 있을때)
        SignUpForm form = SignUpForm.builder()
                .userId("zerobase123")
                .password("qwerty!23")
                .checkedPassword("qwerty!89")
                .username("홍길동")
                .build();

        //when(어떤 동작을 하게되면)
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        ResponseEntity<?> response = userController.signUpUser(form, bindingResult);

        //then(어떤 결과가 나와야한다)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals(ErrorCode.PASSWORD_UNMATCHED, errorResponse.getErrorCode());
        assertEquals(ErrorCode.PASSWORD_UNMATCHED.getDescription(), errorResponse.getErrorMessages().get(0));
        assertEquals("404", errorResponse.getHttpStatus());
    }

    @DisplayName("회원가입 성공 테스트")
    @Test
    void signUpControllerTest3() throws Exception {
        //given(어떤 데이터가 있을때)
        SignUpForm form = SignUpForm.builder()
                .userId("zerobase123")
                .password("qwerty!23")
                .checkedPassword("qwerty!23")
                .username("홍길동")
                .build();

        UserResponseDto user = UserResponseDto.builder()
                .userId("zerobase123")
                .build();

        //when(어떤 동작을 하게되면)
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
//        when(userService.signUp(form)).thenReturn(user);
//        when(userService.signUp(form)).thenReturn(UserResponseDto.builder().userId("zerobase123").build());
        when(userRepository.findByUserId(form.getUserId())).thenReturn(Optional.empty());
        ResponseEntity<?> response = userController.signUpUser(form, bindingResult);

        //then(어떤 결과가 나와야한다)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserResponseDto userResponseDto = (UserResponseDto) response.getBody();
        assertEquals("zerobase123", userResponseDto.getUserId());
    }

    @DisplayName("로그인 성공 테스트")
    @Test
    void signInControllerTest1() {
        //given(어떤 데이터가 있을때)
        SignInForm form = SignInForm.builder()
                .userId("zerobase123")
                .password("qwerty!23")
                .build();

        User user = User.builder()
                .userId("zerobase123")
                .password("$2a$10$Q5wbv/PLfG.Abpx1QSDtOuVtLXKbB3m8BYt0xt/6mv.zDCvSlq74i") // qwerty!23
                .username("홍길동")
                .id(2L)
                .build();

        //when(어떤 동작을 하게되면)
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        ResponseEntity<?> response = userController.signInUser(form, bindingResult);
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(user));

        //then(어떤 결과가 나와야한다)
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String token = (String) response.getBody();
        Jws<Claims> parse = tokenService.parseToken(token);

        assertEquals("zerobase123", parse.getBody().get("userId"));
    }
}