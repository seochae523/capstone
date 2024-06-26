package com.sejong.capstone.subtitle.repository;

import com.sejong.capstone.subtitle.domain.Subtitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtitleRepository extends JpaRepository<Subtitle, Long> {
    @Query("select s from subtitle s where s.user.email=:userEmail")
    List<Subtitle> findByUserEmail(@Param("userEmail") String userEmail);
}
