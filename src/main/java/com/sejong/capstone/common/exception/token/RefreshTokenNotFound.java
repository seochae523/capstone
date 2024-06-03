package com.sejong.capstone.common.exception.token;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class RefreshTokenNotFound extends RuntimeException{
    public RefreshTokenNotFound() {
        super();
    }
}
