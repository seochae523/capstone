package com.sejong.capstone.common.exception.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
public class StudentIdNotFound extends RuntimeException{
    public StudentIdNotFound() {
        super();
    }
}
