package com.example.demo.api;

import com.example.demo.domain.horoscope.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/horoscopes")
@RequiredArgsConstructor
public class HoroscopeController {

    private final HoroscopeService horoscopeService;

    @GetMapping
    public List<Horoscope> getAllHoroscopes() {
        return horoscopeService.getAllHoroscopes();
    }

    @GetMapping("/{zodiac}/{period}")
    public Horoscope getHoroscope(
            @PathVariable String zodiac,
            @PathVariable String period
    ) {
        Zodiac zodiacEnum = Zodiac.valueOf(zodiac);
        Period periodEnum = Period.valueOf(period);
        return horoscopeService.getHoroscope(zodiacEnum, periodEnum);
    }
}