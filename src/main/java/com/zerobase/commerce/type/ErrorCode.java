package com.zerobase.commerce.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR("내부 서버 오류가 발생했습니다."),
    INVALID_REQUEST("잘못된 요청입니다."),
    PASSWORD_UNMATCHED("비밀번호기 일치하지 않습니다."),
    USERID_ALREADY_EXIST("이미 존재하는 아이디입니다."),
    TOKEN_EXPIRED("인증 토큰이 만료되었습니다."),
    USER_NOT_FOUND("회원 정보가 존재하지 않습니다.");
    private final String description;
}
