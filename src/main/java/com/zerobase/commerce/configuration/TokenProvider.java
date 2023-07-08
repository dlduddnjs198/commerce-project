package com.zerobase.commerce.configuration;

import com.zerobase.commerce.user.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class TokenProvider {
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days
    private static final long TOKEN_EXPIRE_TIME_TEST1 = 10000; // 10 seconds
    private static final long TOKEN_EXPIRE_TIME_TEST2 = 1000 * 60 * 60; // 1 hour

    private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    /**
     * 토큰 생성(발급)
     *
     * @param userId
     * @return
     */
    public String generateToken(String userId, String role) {

        Claims claims = Jwts.claims(); // payload에 담을 값(토큰에 담을 정보를 key-value 형태로 저장한다.)
        claims.put("userId", userId);
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


}
