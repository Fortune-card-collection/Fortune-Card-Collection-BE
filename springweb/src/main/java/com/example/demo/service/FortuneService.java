package com.example.demo.service;

import com.example.demo.domain.fortune.PersonalFortune;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FortuneService {

    private final ChatService chatService;
    private final UserRepository userRepository;

    @Transactional
    public PersonalFortune generateTodayFortune(User sessionUser) {
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (user.getBirthDate() == null) {
            throw new RuntimeException("생년월일 정보가 없습니다.");
        }

        return chatService.getOrCreateTodayFortune(user);
    }

    @Transactional(readOnly = true)
    public PersonalFortune getTodayFortune(User sessionUser) {
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        return chatService.getTodayFortune(user);
    }
}