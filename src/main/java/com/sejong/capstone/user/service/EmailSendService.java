package com.sejong.capstone.user.service;


import com.sejong.capstone.common.configuration.EmailConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;


// 11/25 추가 : 이메일 서비스
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmailSendService {
    private final EmailConfig emailConfig;
    public void sendEmail(String toEmail,
                          String title,
                          String authCode, Integer type) {
        Properties prop = emailConfig.getMailProperties();
        Session session = Session.getInstance(prop, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(emailConfig.getUsername(), emailConfig.getPassword());
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailConfig.getUsername()+"@gmail.com", emailConfig.getUsername(), "utf-8"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            String msg = "";
            if(type == 0) {
                msg += "<div style=\"text-align: center; align-items: center\">\n" +
                        "  <div\n" +
                        "    style=\"\n" +
                        "      border-bottom: 1px solid black;\n" +
                        "      width: 80%;\n" +
                        "      margin: 0 auto;\n" +
                        "      padding: 1px;\n" +
                        "    \"\n" +
                        "  >\n" +
                        "    <h1 style=\"text-align: center\">회원가입 인증 메일</h1>\n" +
                        "  </div>\n" +
                        "  <br />\n" +
                        "  <br />\n" +
                        "  <div style=\"color: #8c8c8c\">\n" +
                        "    <a>아래의 인증코드를 인증 창에 입력하여</a>\n" +
                        "    <br />\n" +
                        "    <a>회원 가입을 완료해 주세요.</a>\n" +
                        "  </div>\n" +
                        "  <br />\n" +
                        "  <div\n" +
                        "    style=\"\n" +
                        "      border-radius: 8px;\n" +
                        "      width: 50%;\n" +
                        "      border: 1px solid black;\n" +
                        "      margin: 0 auto;\n" +
                        "      background: #e2e2e2;\n" +
                        "    \"\n" +
                        "  >\n" +
                        "    <h1 style=\"font-size: 50px\">" + authCode + "</h1>\n" +
                        "  </div>\n" +
                        "\n" +
                        "  <br />\n" +
                        "  <div style=\"color: #8c8c8c\">\n" +
                        "    <a>본 이메일 인증은 회원가입을 위한 필수 사항입니다.</a>\n" +
                        "    <br />\n" +
                        "    <a>인증코드를 어플 인증창에 직접 입력해 주세요.</a>\n" +
                        "  </div>\n" +
                        "</div>\n";
            }
            else{
                msg += "<div style=\"text-align: center; align-items: center\">\n" +
                        "  <div\n" +
                        "    style=\"\n" +
                        "      border-bottom: 1px solid black;\n" +
                        "      width: 80%;\n" +
                        "      margin: 0 auto;\n" +
                        "      padding: 1px;\n" +
                        "    \"\n" +
                        "  >\n" +
                        "    <h1 style=\"text-align: center\">비밀번호 재설정 인증 메일</h1>\n" +
                        "  </div>\n" +
                        "  <br />\n" +
                        "  <br />\n" +
                        "  <div style=\"color: #8c8c8c\">\n" +
                        "    <a>아래의 인증코드를 인증 창에 입력하여</a>\n" +
                        "    <br />\n" +
                        "    <a>비밀번호 재설정을 완료해 주세요.</a>\n" +
                        "  </div>\n" +
                        "  <br />\n" +
                        "  <div\n" +
                        "    style=\"\n" +
                        "      border-radius: 8px;\n" +
                        "      width: 50%;\n" +
                        "      border: 1px solid black;\n" +
                        "      margin: 0 auto;\n" +
                        "      background: #e2e2e2;\n" +
                        "    \"\n" +
                        "  >\n" +
                        "    <h1 style=\"font-size: 50px\">" + authCode + "</h1>\n" +
                        "  </div>\n" +
                        "\n" +
                        "  <br />\n" +
                        "  <div style=\"color: #8c8c8c\">\n" +
                        "    <a>본 이메일 인증은 비밀번호 재설정을 위한 필수 사항입니다.</a>\n" +
                        "    <br />\n" +
                        "    <a>인증코드를 어플 인증창에 직접 입력해 주세요.</a>\n" +
                        "  </div>\n" +
                        "</div>\n";
            }

            message.setSubject(title);
            message.setText(msg, "utf-8", "html");

            Transport.send(message);
        }
        catch (AddressException e){
            log.error(e.getMessage());
        }
        catch (MessagingException e){
            log.error(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}