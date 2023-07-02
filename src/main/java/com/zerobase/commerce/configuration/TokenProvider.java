package com.zerobase.commerce.configuration;

import com.zerobase.commerce.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days
    private static final long TOKEN_EXPIRE_TIME_TEST1 = 10000; // 10 seconds
    private static final long TOKEN_EXPIRE_TIME_TEST2 = 1000 * 60 * 60; // 1 hour

    private final UserService userService;

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    /**
     * 토큰 생성(발급)
     *
     * @param userId
     * @return
     */
    public String generateToken(String userId, String role) {

        Claims claims = Jwts.claims().setId(userId); // payload에 담을 값(토큰에 담을 정보를 key-value 형태로 저장한다.)
        claims.put("role", role);

        var now = new Date();
        var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME_TEST2);

        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .setClaims(claims)
                .setIssuedAt(now) // 토큰 생성 시간
                .setExpiration(expiredDate) // 토큰 만료 시간
                .signWith(key) // 사용할 암호화 알고리즘
                .compact();
    }

    /**
     * 토큰 파싱
     *
     * @param token
     * @return
     */
    public Jws<Claims> parseToken(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return claims;
    }
}
