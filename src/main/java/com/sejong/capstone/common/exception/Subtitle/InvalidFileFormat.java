package com.sejong.capstone.common.exception.Subtitle;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;

@Getter
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidFileFormat extends RuntimeException{
    String message;

    public InvalidFileFormat(byte[] message) {
        super(Arrays.toString(message));
        this.message = Arrays.toString(message);
    }
}
