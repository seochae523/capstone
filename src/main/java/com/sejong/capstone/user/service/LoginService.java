package com.sejong.capstone.user.service;



import com.sejong.capstone.user.dto.request.LoginRequestDto;
import com.sejong.capstone.user.dto.request.SignUpRequestDto;


public interface LoginService{
    Object login(LoginRequestDto loginRequestDto);
    Object logout(String email);
    Object signup(SignUpRequestDto signUpRequestDto);
}
