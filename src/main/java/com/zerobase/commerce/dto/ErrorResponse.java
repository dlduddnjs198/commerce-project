package com.zerobase.commerce.dto;

import com.zerobase.commerce.type.ErrorCode;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@ToString
public class ErrorResponse {
    private ErrorCode errorCode;
    private String httpStatusCode;
    private List<String> errorMessages;

    public ErrorResponse(ErrorCode errorCode, String httpStatusCode, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatusCode = httpStatusCode;
        this.errorMessages = new ArrayList<>();
        this.errorMessages.add(errorMessage);
    }

    public ErrorResponse(ErrorCode errorCode, String httpStatusCode, List<String> errorMessages) {
        this.errorCode = errorCode;
        this.httpStatusCode = httpStatusCode;
        this.errorMessages = errorMessages;
    }
}
