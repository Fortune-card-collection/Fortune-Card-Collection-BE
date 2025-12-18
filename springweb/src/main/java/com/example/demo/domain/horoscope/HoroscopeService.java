package com.example.demo.domain.horoscope;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HoroscopeService {

    private final HoroscopeRepository horoscopeRepository;

    public List<Horoscope> getAllHoroscopes() {
        return horoscopeRepository.findAll();
    }

    public Horoscope getHoroscope(Zodiac zodiac, Period period) {
        return horoscopeRepository.findByZodiacAndPeriod(zodiac, period)
                .orElseThrow(() -> new RuntimeException("해당 별자리/기간 운세가 없습니다."));
    }
}