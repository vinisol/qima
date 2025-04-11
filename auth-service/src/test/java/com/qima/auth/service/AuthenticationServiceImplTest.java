package com.qima.auth.service;

import com.qima.auth.domain.User;
import com.qima.auth.dto.LoginRequest;
import com.qima.auth.dto.LoginResponse;
import com.qima.auth.repository.UserRepository;
import com.qima.auth.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthenticationServiceImplTest {

    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        jwtUtil = mock(JwtUtil.class);
        authenticationService = new AuthenticationServiceImpl(userRepository, jwtUtil);
    }

    @Test
    void givenValidUsernameAndPassword_whenAuthenticate_thenReturnToken() {
        LoginRequest loginRequest = new LoginRequest("username", "password");
        User user = new User();
        user.setUsername(loginRequest.getUsername());
        user.setPassword(loginRequest.getPassword());
        user.setRole("ROLE_USER");
        String token = "mockedToken";

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(user.getUsername(), user.getRole())).thenReturn(token);

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(user));
        when(jwtUtil.generateToken(user.getUsername(), user.getRole())).thenReturn(token);

        LoginResponse response = authenticationService.authenticate(loginRequest);
        assertEquals(token, response.getToken());
    }

    @Test
    void givenInvalidUsername_whenAuthenticate_thenThrowException() {
        LoginRequest loginRequest = new LoginRequest("username", "password");

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authenticationService.authenticate(loginRequest));
    }

    @Test
    void givenInvalidPassword_whenAuthenticate_thenThrowException() {
        LoginRequest loginRequest = new LoginRequest("username", "wrongPassword");
        User user = new User();
        user.setUsername(loginRequest.getUsername());
        user.setPassword("password");

        when(userRepository.findByUsername(loginRequest.getUsername())).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> authenticationService.authenticate(loginRequest));
    }
}
