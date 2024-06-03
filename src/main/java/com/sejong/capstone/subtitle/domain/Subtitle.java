package com.sejong.capstone.subtitle.domain;

import com.sejong.capstone.user.domain.User;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;


@Entity(name="subtitle")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Builder
public class Subtitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="subtitle", columnDefinition = "text")
    private String subtitle;

    @Column(name = "created_at", columnDefinition = "timestamp")
    private LocalDateTime createdAt;

    @ManyToOne
    private User user;
}
