package com.sejong.capstone.user.dto.request;

import com.sejong.capstone.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {
    private String name;
    private String email;
    private String password;

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
    }
}
