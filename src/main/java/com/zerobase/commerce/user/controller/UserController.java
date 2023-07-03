package com.zerobase.commerce.user.controller;

import com.zerobase.commerce.dto.ErrorResponse;
import com.zerobase.commerce.user.dto.form.SignInForm;
import com.zerobase.commerce.user.dto.form.SignUpForm;
import com.zerobase.commerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static com.zerobase.commerce.type.ErrorCode.INVALID_REQUEST;
import static com.zerobase.commerce.type.ErrorCode.PASSWORD_UNMATCHED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@Validated @RequestBody SignUpForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            // 200 response with 404 status code
            return ResponseEntity.ok(new ErrorResponse(INVALID_REQUEST, "404", errors));
            // or 404 request
            //  return ResponseEntity.badRequest().body(new ErrorResponse(INVALID_REQUEST, "404", errors));
        }
        if (!form.getPassword().equals(form.getCheckedPassword())) {
            return ResponseEntity.ok(new ErrorResponse(PASSWORD_UNMATCHED, "404", PASSWORD_UNMATCHED.getDescription()));
        }
        return ResponseEntity.ok(userService.signUp(form));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(@Validated @RequestBody SignInForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            // 200 response with 404 status code
            return ResponseEntity.ok(new ErrorResponse(INVALID_REQUEST, "404", errors));
            // or 404 request
            //  return ResponseEntity.badRequest().body(new ErrorResponse(INVALID_REQUEST, "404", errors));
        }

        return ResponseEntity.ok(userService.signIn(form));
    }
}
