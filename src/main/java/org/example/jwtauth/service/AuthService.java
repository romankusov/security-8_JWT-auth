package org.example.jwtauth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.jwtauth.dto.AuthDto;
import org.example.jwtauth.model.User;
import org.example.jwtauth.repository.UserRepository;
import org.example.jwtauth.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SampleUserDetailsService sampleUserDetailsService;

    public AuthDto register(AuthDto authDto) {

        User user = new User();
        user.setEmail(authDto.getEmail());
        user.setPassword(passwordEncoder.encode(authDto.getPassword()));
        user.setRole(authDto.getRole());
        user = userRepository.save(user);
        log.info("User email " + user.getEmail() + " User pass " + user.getPassword());
        authDto.setUser(user);
        authDto.setMessage("User registered successfully");
        log.info("AuthDto + " + authDto);
        return authDto;
    }

    public AuthDto signIn(AuthDto authDto) {
        AuthDto newAuthDto = new AuthDto();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userRepository.findByEmail(authDto.getEmail()).orElseThrow();

            String token = JwtUtil.generateToken(user);
            String refreshToken = JwtUtil.generateRefreshToken(new HashMap<>(), user);
            newAuthDto.setToken(token);
            newAuthDto.setRefreshToken(refreshToken);
            newAuthDto.setExpirationTime("10m");
            newAuthDto.setUser(user);
            newAuthDto.setEmail(user.getEmail());
            newAuthDto.setMessage("User is signed");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return newAuthDto;
    }

    public AuthDto refreshToken(AuthDto authDto) {
        UserDetails userDetails = sampleUserDetailsService.loadUserByUsername(authDto.getEmail());
        String token = JwtUtil.generateToken(userDetails);
        authDto.setToken(token);
        authDto.setMessage("Token refreshed ");
        return authDto;
    }

}
