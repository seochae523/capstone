package com.sejong.capstone.subtitle.dto.response;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class SubtitleSaveResponseDto {
    public String subtitle;
    public String userEmail;
}
