package com.sejong.capstone.pronunciation.service;

import com.sejong.capstone.pronunciation.dto.request.PronunciationRequestDto;
import com.sejong.capstone.pronunciation.dto.response.PronunciationFindResponseDto;
import com.sejong.capstone.pronunciation.dto.response.PronunciationResponseDto;
import com.sejong.capstone.pronunciation.dto.response.SentenceResponseDto;

import java.io.IOException;
import java.util.List;

public interface PronunciationService {
    SentenceResponseDto getOriginalText();

    PronunciationResponseDto pronunciation(PronunciationRequestDto pronunciationRequestDto) throws IOException, InterruptedException;

    List<PronunciationFindResponseDto> findAll(String userEmail);
}
