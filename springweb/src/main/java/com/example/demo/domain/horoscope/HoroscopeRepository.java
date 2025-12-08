package com.example.demo.domain.horoscope;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HoroscopeRepository extends JpaRepository<Horoscope, Long> {

    List<Horoscope> findByZodiac(Zodiac zodiac);

    Optional<Horoscope> findByZodiacAndPeriod(Zodiac zodiac, Period period);
}