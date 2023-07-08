package com.zerobase.commerce.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface TokenService {
    // 토큰 파싱
    public Jws<Claims> parseToken(String token);

    // Request의 Header에서 token 값을 가져온다. "X-AUTH-TOKEN" : "TOKEN값'
    public String resolveToken(HttpServletRequest request);

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token);

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token);
}
