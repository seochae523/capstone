package com.sejong.capstone.pronunciation.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SentenceResponseDto {
    private String originalText;
    @Builder
    public SentenceResponseDto(String originalText) {
        this.originalText = originalText;
    }
}
