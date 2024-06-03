package com.sejong.capstone.user.service.impl;

import com.sejong.capstone.common.emun.Role;
import com.sejong.capstone.common.exception.user.AccountNotFound;
import com.sejong.capstone.common.exception.user.EmailNotFound;
import com.sejong.capstone.common.exception.user.PasswordNotFound;
import com.sejong.capstone.common.exception.user.UserNameNotFound;
import com.sejong.capstone.common.token.AuthTokenProvider;
import com.sejong.capstone.common.token.dto.AuthToken;
import com.sejong.capstone.user.domain.User;
import com.sejong.capstone.user.dto.request.ChangePasswordRequestDto;
import com.sejong.capstone.user.dto.request.LoginRequestDto;
import com.sejong.capstone.user.dto.request.SignUpRequestDto;
import com.sejong.capstone.user.dto.response.ChangePasswordResponseDto;
import com.sejong.capstone.user.dto.response.LoginResponseDto;
import com.sejong.capstone.user.dto.response.SignUpResponseDto;
import com.sejong.capstone.user.repository.UserRepository;
import com.sejong.capstone.user.service.LoginService;
import com.sejong.capstone.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService, UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthTokenProvider authTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();
        if(email == null){
            throw new EmailNotFound();
        }
        if(password == null){
            throw new PasswordNotFound();
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFound(email));

        String role = user.getRole();
        List<String> roles = new ArrayList<>();
        for (String s : role.split(",")) {
            roles.add(s);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        AuthToken authToken = authTokenProvider.generateToken(authentication, roles);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return LoginResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .authToken(authToken)
                .build();
    }

    @Override
    public Boolean logout(String email) {
        return true;
    }


    @Override
    public SignUpResponseDto signup(SignUpRequestDto signUpRequestDto) {
        String email = signUpRequestDto.getEmail();
        String name = signUpRequestDto.getName();
        String password = signUpRequestDto.getPassword();
        if(email == null){
            throw new EmailNotFound();
        }
        if(name == null){
            throw new UserNameNotFound();
        }
        if(password == null){
            throw new PasswordNotFound();
        }

        User user = signUpRequestDto.toEntity();

        user.setRole(Role.USER);
        user.hashPassword(passwordEncoder);

        userRepository.save(user);

        return SignUpResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }


    @Override
    public ChangePasswordResponseDto changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
        String password = changePasswordRequestDto.getPassword();
        String email = changePasswordRequestDto.getEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Account Not Found"));

        user.updatePassword(password);
        user.hashPassword(passwordEncoder);

        userRepository.save(user);

        return ChangePasswordResponseDto.builder()
                .email(email)
                .build();
    }
}
