package com.sejong.capstone.pronunciation.repository;

import com.sejong.capstone.pronunciation.domain.Sentence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SentenceRepository extends JpaRepository<Sentence, Integer> {
    Optional<Sentence> findByOriginalText(@Param("originalText") String originalText);
}
