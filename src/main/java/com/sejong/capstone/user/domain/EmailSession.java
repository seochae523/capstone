package com.sejong.capstone.user.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "EMAIL_SESSION")
public class EmailSession {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String verifyCode;

    private String email;

    @CreationTimestamp
    @Column(name="createdTime")
    private LocalDateTime createdTime;

    public void reSetVerifyCode(String verifyCode, LocalDateTime createdTime) {
        this.verifyCode = verifyCode;
        this.createdTime = createdTime;
    }
}
