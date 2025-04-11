package com.qima.auth.service;

import com.qima.auth.dto.LoginRequest;
import com.qima.auth.dto.LoginResponse;

public interface AuthenticationService {

    LoginResponse authenticate(LoginRequest loginRequest);
}
