package com.zerobase.commerce.exception;

import com.zerobase.commerce.type.ErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public UserException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
