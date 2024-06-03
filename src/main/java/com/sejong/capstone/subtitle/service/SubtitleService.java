package com.sejong.capstone.subtitle.service;

import com.sejong.capstone.common.exception.Subtitle.InvalidFileFormat;
import com.sejong.capstone.pronunciation.domain.Sentence;
import com.sejong.capstone.subtitle.domain.Subtitle;
import com.sejong.capstone.subtitle.dto.request.SubtitleRequestDto;
import com.sejong.capstone.subtitle.dto.request.SubtitleSaveRequestDto;
import com.sejong.capstone.subtitle.dto.response.SubtitleFindResponseDto;
import com.sejong.capstone.subtitle.dto.response.SubtitleGenerateResponseDto;
import com.sejong.capstone.subtitle.dto.response.SubtitleSaveResponseDto;
import com.sejong.capstone.subtitle.repository.SubtitleRepository;
import com.sejong.capstone.user.domain.User;
import com.sejong.capstone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubtitleService {
    private final SubtitleRepository subtitleRepository;
    private final UserRepository userRepository;
    public SubtitleGenerateResponseDto generateSubtitle(SubtitleRequestDto subtitleRequestDto) throws IOException {
//        ProcessBuilder processBuilder = new ProcessBuilder("python", "/resources/mnt/inference.py", "arg1","agr2");
//        Process process = processBuilder.start();
//        InputStream inputStream = process.getInputStream();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        String line;
//        while ((line = reader.readLine()) != null) {
//            // 실행 결과 처리
//            log.info("{}", line);
//        }
        User user = userRepository.findByEmail(subtitleRequestDto.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        byte[] video = subtitleRequestDto.getVideo();
        byte[] audio = subtitleRequestDto.getAudio();



        String subtitle ="안녕하세요";

        Subtitle build = Subtitle.builder().user(user).subtitle(subtitle).build();
        subtitleRepository.save(build);

        return SubtitleGenerateResponseDto.builder()
                .sentence(subtitle)
                .build();
    }

    @Transactional
    public SubtitleSaveResponseDto save(SubtitleSaveRequestDto subtitleSaveRequestDto){

        String userEmail = subtitleSaveRequestDto.getUserEmail();
        String subtitle = subtitleSaveRequestDto.getSubtitle();
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        Subtitle build = Subtitle.builder().createdAt(LocalDateTime.now()).user(user).subtitle(subtitle).build();

        subtitleRepository.save(build);

        return SubtitleSaveResponseDto.builder()
                .userEmail(userEmail)
                .subtitle(subtitle)
                .build();
    }
    public List<SubtitleFindResponseDto> findAll(String userEmail){
        List<Subtitle> result = subtitleRepository.findByUserEmail(userEmail);

        return result
                .stream()
                .map(SubtitleFindResponseDto::new)
                .collect(Collectors.toList());
    }
}
