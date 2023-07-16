package com.zerobase.commerce.exception;

import com.zerobase.commerce.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserException.class)
    public ErrorResponse handleUserException(UserException e) {
        log.error("UserException {} is occurred.", e.getErrorCode());

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ErrorResponse(e.getErrorCode(), httpStatus, e.getErrorMessage());
    }

    @ExceptionHandler(ProductException.class)
    public ErrorResponse handleProductException(ProductException e) {
        log.error("ProductException {} is occurred.", e.getErrorCode());

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ErrorResponse(e.getErrorCode(), httpStatus, e.getErrorMessage());
    }

    @ExceptionHandler(ReviewException.class)
    public ErrorResponse handleProductException(ReviewException e) {
        log.error("ReviewException {} is occurred.", e.getErrorCode());

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        return new ErrorResponse(e.getErrorCode(), httpStatus, e.getErrorMessage());
    }
}
