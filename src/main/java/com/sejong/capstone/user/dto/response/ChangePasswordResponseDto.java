package com.sejong.capstone.user.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordResponseDto {
    private String email;
}
