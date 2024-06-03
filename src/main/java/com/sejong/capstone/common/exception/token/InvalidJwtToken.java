package com.sejong.capstone.common.exception.token;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Getter
public class InvalidJwtToken extends RuntimeException{
    private String message;

    public InvalidJwtToken(String token) {
        super();
        this.message = token;
    }
}
