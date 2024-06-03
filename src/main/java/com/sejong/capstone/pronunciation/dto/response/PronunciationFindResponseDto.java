package com.sejong.capstone.pronunciation.dto.response;

import com.sejong.capstone.pronunciation.domain.Pronunciation;
import lombok.*;


@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PronunciationFindResponseDto {
    private String originalText;
    private String pronunciationText;
    private Double accuracy;

    public PronunciationFindResponseDto(Pronunciation pronunciation){
        this.originalText = pronunciation.getSentence().getOriginalText();
        this.pronunciationText  = pronunciation.getPronunciationText();
        this.accuracy = pronunciation.getAccuracy();
    }
}
