package org.example.jwtauth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jwtauth.dto.AuthDto;
import org.example.jwtauth.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthDto> register(@RequestBody AuthDto authDto) {
        return ResponseEntity.ok(authService.register(authDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthDto> signIn(@RequestBody AuthDto authDto) {
        return ResponseEntity.ok(authService.signIn(authDto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthDto> refreshToken(@RequestBody AuthDto authDto) {
        return ResponseEntity.ok(authService.refreshToken(authDto));
    }
}