package com.sejong.capstone.common.exception.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class UserIdNotFound extends RuntimeException{
    public UserIdNotFound(){
        super();
    }
}
