package com.sejong.capstone.pronunciation.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PronunciationPythonResponseDto {
    private String originalText;
    private String pronunciationText;
}
