package com.example.demo.domain.fortune;

import com.example.demo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface PersonalFortuneRepository extends JpaRepository<PersonalFortune, Long> {
    Optional<PersonalFortune> findByUserAndTargetDate(User user, LocalDate targetDate);
}