package com.sejong.capstone.user.controller;



import com.sejong.capstone.user.dto.request.EmailSendRequestDto;
import com.sejong.capstone.user.dto.response.EmailSendResponseDto;
import com.sejong.capstone.user.service.EmailCreateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user/email")
@Tag(name = "이메일 발송 관련 컨트롤러")
public class EmailController {
    private final EmailCreateService emailCreateService;

    @GetMapping("/verification-request")
    @Operation(summary = "회원가입 이메일 전송", description = "해당 url로 요청시 회원가입 이메일 전송")
    @Parameters({
            @Parameter(name = "email", description = "이메일"),
            @Parameter(name="type", description = "타입 0 = 회원가입 인증메일, 1 = 비번 찾기 인증메일")
    })
    public ResponseEntity<EmailSendResponseDto> sendMessage(@RequestParam(name = "email" , required = false) String email,
                                                            @RequestParam(name="type") Integer type){
        return new ResponseEntity<>(emailCreateService.sendCodeToEmail(email, type), HttpStatus.OK);
    }

    @PostMapping("/verification")
    @Operation(summary = "회원가입 이메일 검증", description = "해당 url로 요청시 회원가입 이메일에 적힌 코드를 검증")
    public ResponseEntity<Boolean> verificationEmail(@RequestBody EmailSendRequestDto emailSendRequestDto){
        return new ResponseEntity<>(emailCreateService.verifyCode(emailSendRequestDto), HttpStatus.OK);
    }


}
