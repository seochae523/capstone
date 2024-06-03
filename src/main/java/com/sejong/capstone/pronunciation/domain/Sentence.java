package com.sejong.capstone.pronunciation.domain;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity(name="sentence")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sentence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="original_text", columnDefinition = "text")
    private String originalText;
}
