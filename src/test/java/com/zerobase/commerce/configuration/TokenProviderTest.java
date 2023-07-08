package com.zerobase.commerce.configuration;

import com.zerobase.commerce.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TokenProviderTest {


    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private TokenService tokenService;

    @DisplayName("JWT 제대로 파싱하는지 테스트")
    @Test
    void TokenParserTest() {
        String token = tokenProvider.generateToken("zerobase157", "USER");

        System.out.println(token);

        Jws<Claims> parse = tokenService.parseToken(token);

        System.out.println(parse.getHeader().toString());
        System.out.println(parse.getBody().toString());
        System.out.println(parse.getSignature().toString());
    }

}