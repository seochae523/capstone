package com.sejong.capstone.user.dto.request;

import lombok.Getter;

@Getter
public class EmailSendRequestDto {
    private String email;
    private String code;
}
