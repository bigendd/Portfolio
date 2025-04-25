package com.amarbelaifa.portfolio.controller;

import com.amarbelaifa.portfolio.model.dto.LoginRequest;
import com.amarbelaifa.portfolio.model.dto.RegisterRequest;
import com.amarbelaifa.portfolio.model.dto.LoginResponse;
import com.amarbelaifa.portfolio.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth/")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest request) {
        LoginResponse response = authService.register(request);
        return ResponseEntity.status(
                response.getMessage().contains("successful") ? HttpStatus.OK : HttpStatus.BAD_REQUEST
        ).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.status(
                response.getMessage().contains("successful") ? HttpStatus.OK : HttpStatus.UNAUTHORIZED
        ).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<LoginResponse> logout() {
        LoginResponse response = authService.logout();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
