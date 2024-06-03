package com.sejong.capstone.pronunciation.domain;

import com.sejong.capstone.user.domain.User;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity(name="pronunciation")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
public class Pronunciation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="pronunciation_text", columnDefinition = "text")
    private String pronunciationText;

    @Column(name="accuracy", columnDefinition = "double")
    private Double accuracy;

    @Column(name = "created_at", columnDefinition = "timeStamp")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    private User user;

    @ManyToOne
    private Sentence sentence;
}
