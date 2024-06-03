package com.sejong.capstone.pronunciation.service;



import com.sejong.capstone.pronunciation.domain.Pronunciation;
import com.sejong.capstone.pronunciation.domain.Sentence;
import com.sejong.capstone.pronunciation.dto.request.PronunciationRequestDto;
import com.sejong.capstone.pronunciation.dto.response.PronunciationFindResponseDto;
import com.sejong.capstone.pronunciation.dto.response.PronunciationPythonResponseDto;
import com.sejong.capstone.pronunciation.dto.response.PronunciationResponseDto;
import com.sejong.capstone.pronunciation.dto.response.SentenceResponseDto;
import com.sejong.capstone.pronunciation.repository.PronunciationRepository;
import com.sejong.capstone.pronunciation.repository.SentenceRepository;
import com.sejong.capstone.user.domain.User;
import com.sejong.capstone.user.repository.UserRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class PronunciationServiceImpl implements PronunciationService{
    private final PronunciationRepository pronunciationRepository;
    private final SentenceRepository sentenceRepository;
    private final UserRepository userRepository;
    @Override
    public SentenceResponseDto getOriginalText() {
        int i = ((int) (Math.random() * 10) + 1);
        Sentence sentence = sentenceRepository.findById(i)
                .orElseThrow(() -> new IllegalArgumentException(String.valueOf(i)));

        return SentenceResponseDto.builder()
                .originalText(sentence.getOriginalText())
                .build();
    }

    @Override
    public PronunciationResponseDto pronunciation(PronunciationRequestDto pronunciationRequestDto) throws IOException, InterruptedException {

        byte[] audio = pronunciationRequestDto.getAudio();
        String originalText = pronunciationRequestDto.getOriginalText();

        Sentence sentence = sentenceRepository.findByOriginalText(originalText)
                .orElseThrow(IllegalArgumentException::new);

        User user = userRepository.findByEmail(pronunciationRequestDto.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        WebClient webClient =
                WebClient
                        .builder()
                        .baseUrl("http://localhost:8000")
                        .build();

        PronunciationPythonResponseDto result = webClient.post()
                .uri("/pronunciation")
                .bodyValue(pronunciationRequestDto)
                .retrieve()
                .bodyToMono(PronunciationPythonResponseDto.class)
                .block();

        log.info("result = {}", result.toString());
        String pronunciationText = result.getPronunciationText();

        // ai 서버에 비디오 오디오 경로 전달
        Double accuracy = this.similarity(pronunciationText, originalText);

        pronunciationRepository.save(Pronunciation.builder()
                .accuracy(accuracy)
                .pronunciationText(pronunciationText)
                .sentence(sentence)
                .user(user)
                .build());

        return PronunciationResponseDto.builder()
                .accuracy(accuracy)
                .pronunciationText(pronunciationText)
                .originalText(originalText)
                .build();
    }

    @Override
    public List<PronunciationFindResponseDto> findAll(String userEmail) {
        List<Pronunciation> result = pronunciationRepository.findByUserEmail(userEmail);

        return result
                .stream()
                .map(PronunciationFindResponseDto::new)
                .collect(Collectors.toList());
    }
    private double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;

        if (s1.length() < s2.length()) {
            longer = s2;
            shorter = s1;
        }

        int longerLength = longer.length();
        if (longerLength == 0) return 1.0;
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
    }

    private int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();
        int[] costs = new int[s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    costs[j] = j;
                } else {
                    if (j > 0) {
                        int newValue = costs[j - 1];

                        if (s1.charAt(i - 1) != s2.charAt(j - 1)) {
                            newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
                        }

                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }

            if (i > 0) costs[s2.length()] = lastValue;
        }

        return costs[s2.length()];
    }
}
