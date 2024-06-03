package com.sejong.capstone.subtitle.dto.request;

import lombok.Getter;

@Getter
public class SubtitleRequestDto {
    private byte[] video;
    private byte[] audio;
    private String userEmail;
}
