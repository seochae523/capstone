package com.sejong.capstone.common.exception.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNameNotFound extends RuntimeException{
    public UserNameNotFound() {
        super();
    }
}
