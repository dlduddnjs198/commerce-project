package com.zerobase.commerce.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.commerce.dto.ErrorResponse;
import com.zerobase.commerce.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import static com.zerobase.commerce.type.ErrorCode.INVALID_REQUEST;
import static com.zerobase.commerce.type.ErrorCode.TOKEN_EXPIRED;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final TokenService tokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 헤더에서 JWT 를 받아옵니다.
        String token = tokenService.resolveToken((HttpServletRequest) request);
        // 유효한 토큰인지 확인합니다.
        try {
            if (token != null && tokenService.validateToken(token)) {
                // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
                Authentication authentication = tokenService.getAuthentication(token);
                // SecurityContext 에 Authentication 객체를 저장합니다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            // 새로운 토큰 발급 또는 로그인 페이지로 리디렉션 등의 처리를 수행
            // 예: HttpServletResponse를 사용하여 적절한 응답 반환
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getWriter(), new ErrorResponse(TOKEN_EXPIRED, HttpStatus.UNAUTHORIZED, TOKEN_EXPIRED.getDescription()));
            return;
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getWriter(), new ErrorResponse(INVALID_REQUEST, HttpStatus.NOT_FOUND, INVALID_REQUEST.getDescription()));
            return;
        }

        chain.doFilter(request, response);
    }
}
