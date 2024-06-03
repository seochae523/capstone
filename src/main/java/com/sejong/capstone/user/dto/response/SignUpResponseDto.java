package com.sejong.capstone.user.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpResponseDto {
    private String name;
    private String email;

}
