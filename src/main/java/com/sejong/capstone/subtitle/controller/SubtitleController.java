package com.sejong.capstone.subtitle.controller;

import com.sejong.capstone.subtitle.dto.request.SubtitleRequestDto;
import com.sejong.capstone.subtitle.dto.request.SubtitleSaveRequestDto;
import com.sejong.capstone.subtitle.dto.response.SubtitleFindResponseDto;
import com.sejong.capstone.subtitle.dto.response.SubtitleGenerateResponseDto;
import com.sejong.capstone.subtitle.dto.response.SubtitleSaveResponseDto;
import com.sejong.capstone.subtitle.service.SubtitleService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subtitle")
public class SubtitleController {
    private final SubtitleService subtitleService;

//    @MessageMapping("/video")
//    public ResponseEntity<SubtitleGenerateResponseDto> generateSubtitle(SubtitleRequestDto subtitleRequestDto) throws IOException {
//        return ResponseEntity.ok(subtitleService.generateSubtitle(subtitleRequestDto));
//    }

    @GetMapping
    public ResponseEntity<List<SubtitleFindResponseDto>> findAll(@RequestParam("userEmail") String userEmail) throws IOException {
        return ResponseEntity.ok(subtitleService.findAll(userEmail));
    }

    @PostMapping
    public ResponseEntity<SubtitleSaveResponseDto> save(@RequestBody SubtitleSaveRequestDto subtitleSaveRequestDto){
        return ResponseEntity.ok(subtitleService.save(subtitleSaveRequestDto));
    }
}
