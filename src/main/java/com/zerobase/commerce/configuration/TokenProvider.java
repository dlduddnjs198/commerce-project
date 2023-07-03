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

    private final UserService userService;

    private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    /**
     * 토큰 생성(발급)
     *
     * @param userId
     * @return
     */
    public static String generateToken(String userId, String role) {

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

    // Request의 Header에서 token 값을 가져온다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = parseToken(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getStackTrace().toString());
            return false;
        }
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        Jws<Claims> claims = parseToken(token);
        String userId = claims.getBody().get("userId").toString();
        UserDetails userDetails = userService.loadUserByUsername(userId);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
