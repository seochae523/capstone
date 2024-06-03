package com.sejong.capstone.user.service;



import com.sejong.capstone.common.exception.user.EmailNotFound;
import com.sejong.capstone.user.domain.EmailSession;
import com.sejong.capstone.user.dto.request.EmailSendRequestDto;
import com.sejong.capstone.user.dto.response.EmailSendResponseDto;
import com.sejong.capstone.user.repository.EmailRepository;
import com.sejong.capstone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.python.modules._imp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

// 11/25 추가 : 이메일 전송 기능
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EmailCreateService {
    private final UserRepository userRepository;
    private final EmailSendService emailService;
    private final EmailRepository emailRepository;
    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    public EmailSendResponseDto sendCodeToEmail(String toEmail, Integer type) {
        if(toEmail == null) throw new EmailNotFound();
        if(type==0)  this.checkDuplicatedEmail(toEmail);

        String title = "리딩립스 이메일 인증";
        String authCode = this.createCode();

        emailService.sendEmail(toEmail, title, authCode, type);

        EmailSendResponseDto emailSendResponseDto = EmailSendResponseDto.builder()
                .email(toEmail)
                .createdAt(LocalDateTime.now())
                .code(authCode)
                .build();

        EmailSession entity = emailSendResponseDto.toEntity();

        entity.reSetVerifyCode(authCode, LocalDateTime.now());
        emailRepository.save(entity);
        return emailSendResponseDto;
    }

    private void checkDuplicatedEmail(String email){
        if(userRepository.findByEmail(email).isPresent()){
            throw new IllegalArgumentException(email);
        }
    }

    private String createCode() {
        int length = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.debug("MemberService.createCode() exception occur");
            throw new RuntimeException();
        }
    }

    // 이메일 인증
    public Boolean verifyCode(EmailSendRequestDto emailSendRequestDto){
        String email = emailSendRequestDto.getEmail();
        String code = emailSendRequestDto.getCode();



        EmailSession authInfo = emailRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("now found "));

        String AuthCode = authInfo.getVerifyCode();

        LocalDateTime createdTime = authInfo.getCreatedTime();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdTime, now);

        boolean authResult = (duration.toMillis() <= authCodeExpirationMillis) && AuthCode.equals(code);

        if(duration.toMillis() > authCodeExpirationMillis){
            throw new IllegalArgumentException("Email : " + email + " Code : "+ code);
        }
        else if(!AuthCode.equals(code)){
            throw new IllegalArgumentException("Email : " + email + " Code : "+ code);
        }

        if(authResult){
            emailRepository.delete(authInfo);
        }
        return authResult;
    }
}
