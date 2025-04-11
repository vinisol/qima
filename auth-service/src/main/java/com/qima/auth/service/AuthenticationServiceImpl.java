package com.qima.auth.service;

import com.qima.auth.domain.User;
import com.qima.auth.dto.LoginRequest;
import com.qima.auth.dto.LoginResponse;
import com.qima.auth.repository.UserRepository;
import com.qima.auth.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (user.getPassword().equals(loginRequest.getPassword())) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(jwtUtil.generateToken(user.getUsername(), user.getRole()));
            return loginResponse;
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }
}
