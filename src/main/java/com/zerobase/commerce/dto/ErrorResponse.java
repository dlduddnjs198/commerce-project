package com.zerobase.commerce.dto;

import com.zerobase.commerce.type.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@ToString
public class ErrorResponse {
    private ErrorCode errorCode;
    private HttpStatus httpStatus;
    private List<String> errorMessages;

    public ErrorResponse(ErrorCode errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessages = new ArrayList<>();
        this.errorMessages.add(errorMessage);
    }

    public ErrorResponse(ErrorCode errorCode, HttpStatus httpStatus, List<String> errorMessages) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessages = errorMessages;
    }
}
