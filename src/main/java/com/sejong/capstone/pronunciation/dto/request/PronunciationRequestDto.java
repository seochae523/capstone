package com.sejong.capstone.pronunciation.dto.request;

import lombok.Getter;

@Getter
public class PronunciationRequestDto {
    private byte[] audio;
    private String originalText;
    private String userEmail;
}
