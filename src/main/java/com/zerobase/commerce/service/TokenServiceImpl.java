package com.zerobase.commerce.service;

import com.zerobase.commerce.user.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final UserService userService;

    private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);


    // 토큰 파싱
    @Override
    public Jws<Claims> parseToken(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return claims;
    }

    // Request의 Header에서 token 값을 가져온다. "X-AUTH-TOKEN" : "TOKEN값'
    @Override
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    @Override
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
    @Override
    public Authentication getAuthentication(String token) {
        Jws<Claims> claims = parseToken(token);
        String userId = claims.getBody().get("userId").toString();
        UserDetails userDetails = userService.loadUserByUsername(userId);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
