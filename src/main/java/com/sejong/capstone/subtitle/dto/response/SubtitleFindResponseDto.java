package com.sejong.capstone.subtitle.dto.response;

import com.sejong.capstone.subtitle.domain.Subtitle;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubtitleFindResponseDto {
    private String subtitle;
    private LocalDateTime createdAt;

    public SubtitleFindResponseDto(Subtitle subtitle){
        this.subtitle = subtitle.getSubtitle();
        this.createdAt = subtitle.getCreatedAt();
    }
}
