package com.sejong.capstone.user.controller;


import com.sejong.capstone.common.token.dto.AuthToken;
import com.sejong.capstone.user.dto.request.ChangePasswordRequestDto;
import com.sejong.capstone.user.dto.request.LoginRequestDto;
import com.sejong.capstone.user.dto.request.SignUpRequestDto;
import com.sejong.capstone.user.dto.response.ChangePasswordResponseDto;
import com.sejong.capstone.user.dto.response.SignUpResponseDto;
import com.sejong.capstone.user.service.LoginService;
import com.sejong.capstone.user.service.impl.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class LoginController {

    private final LoginServiceImpl loginService;

    @PostMapping("/login")
    public ResponseEntity<AuthToken> login(@RequestBody LoginRequestDto loginRequestDto){
        return new ResponseEntity(loginService.login(loginRequestDto), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        return new ResponseEntity(loginService.signup(signUpRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/change-password")
    public ResponseEntity<ChangePasswordResponseDto> changePassword(@RequestBody ChangePasswordRequestDto changePasswordRequestDto){
        return new ResponseEntity(loginService.changePassword(changePasswordRequestDto), HttpStatus.CREATED);
    }
}
