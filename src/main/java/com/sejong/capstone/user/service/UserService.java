package com.sejong.capstone.user.service;

import com.sejong.capstone.user.dto.request.ChangePasswordRequestDto;
import com.sejong.capstone.user.dto.response.ChangePasswordResponseDto;

public interface UserService {
    ChangePasswordResponseDto changePassword(ChangePasswordRequestDto changePasswordRequestDto);
}
