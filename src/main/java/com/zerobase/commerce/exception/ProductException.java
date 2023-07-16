package com.zerobase.commerce.exception;

import com.zerobase.commerce.type.ErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public ProductException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
