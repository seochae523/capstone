package com.sejong.capstone.common.token.controller;


import com.sejong.capstone.common.token.dto.RefreshDto;
import com.sejong.capstone.common.token.service.RefreshService;

import lombok.RequiredArgsConstructor;

import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
@RequestMapping("/user/refresh")

public class RefreshController {
    private final RefreshService refreshService;

    @PostMapping
    public ResponseEntity refresh(@RequestBody RefreshDto refreshDto) throws ParseException {
        return new ResponseEntity(refreshService.refresh(refreshDto), HttpStatus.OK);
    }
}
