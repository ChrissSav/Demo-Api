package com.example.demo.controller;


import com.example.demo.dto.BaseResponse;
import com.example.demo.dto.auth.*;
import com.example.demo.service.AuthService;
import com.example.demo.util.Util;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    public BaseResponse<AuthenticationResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return Util.generateSuccessResponse(authService.register(registerRequest));

    }

    @PostMapping("/login")
    public BaseResponse<AuthenticationResponse> saveUser(@Valid @RequestBody LoginRequest loginRequest) {
        return Util.generateSuccessResponse(authService.login(loginRequest));
    }

    @PostMapping("/token-refresh")
    public BaseResponse<AccessRefreshTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return Util.generateSuccessResponse(authService.refreshToken(refreshTokenRequest.getRefreshToken()));
    }
}