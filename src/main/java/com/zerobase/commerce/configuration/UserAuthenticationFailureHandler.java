package com.zerobase.commerce.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    // 로그인 인증이 실패했을 때 호출되는 메서드이다. 이 메서드를 재정의하여 실패 처리 로직을 구현할 수 있다.
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // 실패 메시지 설정: exception 객체를 통해 발생한 예외에 따라 실패 메시지를 결정한다.
        String msg = "로그인에 실패하였습니다.";

        // 만약 InternalAuthenticationServiceException 예외가 발생했다면 해당 예외에서 보내오는 메시지를 사용한다.
        if (exception instanceof InternalAuthenticationServiceException) {
            msg = exception.getMessage();
        }

        setUseForward(true);
        setDefaultFailureUrl("/errorc");

        request.setAttribute("errorMessage", msg);
//        getRedirectStrategy().sendRedirect(request, response, "/user/login?error=로그인에 실패하였습니다.");
        super.onAuthenticationFailure(request, response, exception);
    }
}
