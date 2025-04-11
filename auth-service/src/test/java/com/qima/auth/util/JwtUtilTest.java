package com.qima.auth.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private String token;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        token = jwtUtil.generateToken("username", "ROLE_USER");
    }

    @Test
    void givenToken_whenExtractUsername_thenReturnUsername() {
        String username = jwtUtil.extractUsername(token);
        assertEquals("username", username);
    }

    @Test
    void givenToken_whenExtractExpiration_thenReturnExpiration() {
        Date expiration = jwtUtil.extractExpiration(token);
        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void givenToken_whenValidateToken_thenTokenIsValid() {
        assertTrue(jwtUtil.validateToken(token, "username"));
        assertFalse(jwtUtil.validateToken(token, "wronguser"));
    }
}
