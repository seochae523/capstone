package com.sejong.capstone.common.exception.token;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidRefreshToken extends RuntimeException{
    private String message;

    public InvalidRefreshToken(String refreshToken) {
        super();
        this.message = refreshToken;
    }
}
