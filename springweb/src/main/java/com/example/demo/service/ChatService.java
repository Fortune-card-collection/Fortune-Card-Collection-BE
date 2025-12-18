package com.example.demo.service;

import com.example.demo.domain.fortune.PersonalFortune;
import com.example.demo.domain.fortune.PersonalFortuneRepository;
import com.example.demo.domain.user.LunarType;
import com.example.demo.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final PersonalFortuneRepository personalFortuneRepository;

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api-url}")
    private String apiUrl;

    @Transactional
    public PersonalFortune getOrCreateTodayFortune(User user) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));

        Optional<PersonalFortune> existingFortune = personalFortuneRepository.findByUserAndTargetDate(user, today);

        if (existingFortune.isPresent()) {
            return existingFortune.get();
        }

        String fortuneContent = callChatGptApi(user);

        PersonalFortune newFortune = PersonalFortune.builder()
                .user(user)
                .content(fortuneContent)
                .targetDate(today)
                .build();

        return personalFortuneRepository.save(newFortune);
    }

    @Transactional(readOnly = true)
    public PersonalFortune getTodayFortune(User user) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        return personalFortuneRepository.findByUserAndTargetDate(user, today)
                .orElseThrow(() -> new RuntimeException("오늘의 운세가 아직 생성되지 않았습니다."));
    }
    private String callChatGptApi(User user) {
        String prompt = createFortunePrompt(user);
        ChatRequest requestDto = new ChatRequest(model, prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<ChatRequest> entity = new HttpEntity<>(requestDto, headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<ChatResponse> response = restTemplate.postForEntity(apiUrl, entity, ChatResponse.class);
            if (response.getBody() == null || response.getBody().getChoices() == null || response.getBody().getChoices().isEmpty()) {
                throw new RuntimeException("운세 응답 없음");
            }
            return response.getBody().getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            log.error("API Error", e);
            throw new RuntimeException("운세 서비스 연결 실패");
        }
    }

    private String createFortunePrompt(User user) {
        String lunarStr = (user.getLunarType() == LunarType.lunar) ? "음력" : "양력";
        return String.format("%s %s %s에 태어난 %s의 오늘의 운세를 2줄 이내로 알려줘.",
                lunarStr, user.getBirthDate(), user.getBirthTime(), user.getGender());
    }

    @Data public static class ChatRequest {
        private String model;
        private List<Message> messages;
        public ChatRequest(String model, String prompt) {
            this.model = model;
            this.messages = List.of(new Message("user", prompt));
        }
    }
    @Data public static class ChatResponse { private List<Choice> choices; }
    @Data public static class Choice { private Message message; }
    @Data @AllArgsConstructor @RequiredArgsConstructor public static class Message { private String role; private String content; }
}