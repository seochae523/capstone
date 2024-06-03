package com.sejong.capstone.user.repository;



import com.sejong.capstone.user.domain.EmailSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface EmailRepository extends JpaRepository<EmailSession, Long> {
    Optional<EmailSession> findByEmail(@Param("email") String email);

}
