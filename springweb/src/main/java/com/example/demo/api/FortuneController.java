package com.example.demo.api;

import com.example.demo.domain.fortune.PersonalFortune;
import com.example.demo.domain.user.User;
import com.example.demo.service.ChatService; // FortuneService 대신 ChatService 사용
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/fortune/personal")
@RequiredArgsConstructor
public class FortuneController {

    private final ChatService chatService; // ChatService 주입

    @PostMapping("/today")
    public ResponseEntity<?> createTodayFortune(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        try {
            PersonalFortune fortune = chatService.getOrCreateTodayFortune(user);

            return ResponseEntity.ok(Map.of(
                    "date", fortune.getTargetDate(),
                    "message", fortune.getContent()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/today")
    public ResponseEntity<?> getTodayFortune(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        try {
            PersonalFortune fortune = chatService.getTodayFortune(user);

            return ResponseEntity.ok(Map.of(
                    "date", fortune.getTargetDate(),
                    "message", fortune.getContent()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }
}