package com.sejong.capstone.pronunciation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PronunciationResponseDto {
    private String originalText;
    private String pronunciationText;
    private Double accuracy;


}
