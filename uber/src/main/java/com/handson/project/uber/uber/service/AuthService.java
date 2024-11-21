package com.handson.project.uber.uber.service;

import com.handson.project.uber.uber.dto.DriverDto;
import com.handson.project.uber.uber.dto.SignupDto;
import com.handson.project.uber.uber.dto.UserDto;

public interface AuthService {
    String[] login(String email, String password);
    UserDto signUp(SignupDto signupDto);

    DriverDto onBoardNewDriver(Long userId);

    String refreshToken(String token);
}
