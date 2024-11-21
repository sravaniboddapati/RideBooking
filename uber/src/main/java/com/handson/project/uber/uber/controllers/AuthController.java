package com.handson.project.uber.uber.controllers;

import com.handson.project.uber.uber.advices.ApiResponse;
import com.handson.project.uber.uber.dto.*;
import com.handson.project.uber.uber.entities.User;
import com.handson.project.uber.uber.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/signUp")
    public UserDto signUp(@RequestBody SignupDto signupDto){
        return authService.signUp(signupDto);
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

        String[] tokens = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        Cookie cookie = new Cookie("token", tokens[1]);
        cookie.setHttpOnly(true);

        httpServletResponse.addCookie(cookie);
        return new ApiResponse<>(tokens[0]);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/onBoardNewDriver/{userid}")
    public ResponseEntity<DriverDto> onBoardNewDriver(@PathVariable Long userid){
        return ResponseEntity.ok(authService.onBoardNewDriver(userid));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies()).
                filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));

        String accessToken = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(new LoginResponseDto(accessToken));
    }
}
