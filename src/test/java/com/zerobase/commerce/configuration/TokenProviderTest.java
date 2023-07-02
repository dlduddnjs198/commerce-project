package com.zerobase.commerce.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Test
    void TokenParserTest() {
        String token = tokenProvider.generateToken("zerobase157", "USER");

        System.out.println(token);

        Jws<Claims> parse = tokenProvider.parseToken(token);

        System.out.println(parse.getHeader().toString());
        System.out.println(parse.getBody().toString());
        System.out.println(parse.getSignature().toString());
    }

}