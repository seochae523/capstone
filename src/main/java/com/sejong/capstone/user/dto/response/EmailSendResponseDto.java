package com.sejong.capstone.user.dto.response;

import com.sejong.capstone.user.domain.EmailSession;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailSendResponseDto {
    private String email;
    private String code;
    private LocalDateTime createdAt;

    public EmailSession toEntity(){
        return EmailSession.builder()
                .email(email)
                .verifyCode(code)
                .createdTime(createdAt)
                .build();
    }
}
