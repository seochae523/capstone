package com.sejong.capstone.common.token.service;


import com.sejong.capstone.common.exception.token.IncorrectRefreshToken;
import com.sejong.capstone.common.exception.token.RefreshTokenNotFound;
import com.sejong.capstone.common.exception.token.TokenExpired;
import com.sejong.capstone.common.exception.user.AccountNotFound;
import com.sejong.capstone.common.exception.user.StudentIdNotFound;
import com.sejong.capstone.common.token.AuthTokenProvider;
import com.sejong.capstone.common.token.dto.AuthToken;
import com.sejong.capstone.common.token.dto.RefreshDto;
import com.sejong.capstone.user.domain.User;
import com.sejong.capstone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshService {
    private final UserRepository userRepository;

    private final AuthTokenProvider authTokenProvider;
    public AuthToken refresh(RefreshDto refreshDto) throws ParseException {
        if(validation(refreshDto)) {
            String subject = getSubject(refreshDto);

            Authentication authentication = authTokenProvider.getAuthentication(refreshDto.getAccessToken());

            User findUser = userRepository.findByEmail(subject)
                    .orElseThrow(() -> new AccountNotFound(subject));

            String role = findUser.getRole();
            List<String> roles = new ArrayList<>();

            for (String s : role.split(",")) {
                roles.add(s);
            }

            AuthToken newAuthToken = authTokenProvider.generateToken(authentication, roles);
            findUser.updateRefreshToken(newAuthToken.getRefreshToken());
            userRepository.save(findUser);
            return newAuthToken;
        }
        return null;
    }
    private boolean validation(RefreshDto refreshDto) throws ParseException {
        String refreshToken = refreshDto.getRefreshToken();
        String email = refreshDto.getEmail();
        if(refreshToken == null){
            throw new RefreshTokenNotFound();
        }

        if(email == null){
            throw new StudentIdNotFound();
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AccountNotFound(email));

        String userRefreshToken = user.getRefreshToken();
        if(!userRefreshToken.equals(refreshToken)){
            throw new IncorrectRefreshToken(refreshToken);
        }

        long now = System.currentTimeMillis();
        long expirationTime = getExpireTime(refreshToken);

        if(expirationTime - now <= 0){
            throw new TokenExpired(refreshToken);
        }
        return true;
    }
    private String decode(String refreshToken){
        Base64.Decoder decoder = Base64.getDecoder();
        String[] splitJwt = refreshToken.split("\\.");

        String payloadStr = new String(decoder.decode(splitJwt[1].getBytes()));

        return payloadStr;
    }

    private long getExpireTime(String refreshToken) throws ParseException {
        String payload = this.decode(refreshToken);
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(payload);
        long expirationTime = (long) object.get("exp") * 1000;

        return expirationTime;
    }
    private String getSubject(RefreshDto refreshDto) throws ParseException {
        String refreshToken = refreshDto.getRefreshToken();
        String payload = this.decode(refreshToken);
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(payload);
        String subject = object.get("sub").toString();
        return subject;
    }
}
