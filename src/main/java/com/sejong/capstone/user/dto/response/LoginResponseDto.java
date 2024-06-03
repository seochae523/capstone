package com.sejong.capstone.user.dto.response;

import com.sejong.capstone.common.token.dto.AuthToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    private String email;
    private String name;
    private AuthToken authToken;
}
