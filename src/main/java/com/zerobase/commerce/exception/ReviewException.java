package com.zerobase.commerce.exception;

import com.zerobase.commerce.type.ErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ReviewException extends RuntimeException  {
    private ErrorCode errorCode;
    private String errorMessage;

    public ReviewException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
