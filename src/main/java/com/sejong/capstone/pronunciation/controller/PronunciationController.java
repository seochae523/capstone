package com.sejong.capstone.pronunciation.controller;

import com.sejong.capstone.pronunciation.dto.request.PronunciationRequestDto;
import com.sejong.capstone.pronunciation.dto.response.PronunciationFindResponseDto;
import com.sejong.capstone.pronunciation.dto.response.PronunciationResponseDto;
import com.sejong.capstone.pronunciation.dto.response.SentenceResponseDto;
import com.sejong.capstone.pronunciation.repository.SentenceRepository;
import com.sejong.capstone.pronunciation.service.PronunciationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pronunciation")
public class PronunciationController {
    private final PronunciationService pronunciationService;

    @GetMapping("/text")
    public ResponseEntity<SentenceResponseDto> getText(){
        return ResponseEntity.ok(pronunciationService.getOriginalText());
    }

    @PostMapping
    public ResponseEntity<PronunciationResponseDto> pronunciation(@RequestBody PronunciationRequestDto pronunciationRequestDto) throws IOException, InterruptedException {
        return ResponseEntity.ok(pronunciationService.pronunciation(pronunciationRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<PronunciationFindResponseDto>> findAll(@RequestParam("userEmail") String userEmail) {
        return ResponseEntity.ok(pronunciationService.findAll(userEmail));
    }
}

