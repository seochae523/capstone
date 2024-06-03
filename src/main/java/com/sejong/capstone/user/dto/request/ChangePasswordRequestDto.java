package com.sejong.capstone.user.dto.request;

import lombok.Getter;

@Getter
public class ChangePasswordRequestDto {
    private String email;
    private String password;
}
