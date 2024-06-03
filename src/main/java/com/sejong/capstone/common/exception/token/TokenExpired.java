package com.sejong.capstone.common.exception.token;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class TokenExpired extends RuntimeException{
    private String message;

    public TokenExpired(String token) {
        super();
        this.message = token;
    }
}
