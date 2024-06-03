package com.sejong.capstone.pronunciation.repository;

import com.sejong.capstone.pronunciation.domain.Pronunciation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PronunciationRepository extends JpaRepository<Pronunciation, Long> {
    @Query("select p from pronunciation p where p.user.email = :userEmail")
    List<Pronunciation> findByUserEmail(@Param("userEmail") String userEmail);
}
