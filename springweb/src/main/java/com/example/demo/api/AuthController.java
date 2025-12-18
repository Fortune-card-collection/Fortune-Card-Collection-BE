package com.example.demo.api;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import com.example.demo.dto.KakaoTokenResponse;
import com.example.demo.service.KakaoService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final KakaoService kakaoService;
    private final UserRepository userRepository;

    @GetMapping("/auth/kakao/login")
    public ResponseEntity<String> kakaoLogin(@RequestParam("code") String code, HttpServletRequest request) {
        try {
            KakaoTokenResponse tokens = kakaoService.getTokens(code);

            JsonNode userInfo = kakaoService.getKakaoUserInfo(tokens.getAccess_token());
            Long kakaoId = userInfo.get("id").asLong();
            String nickname = userInfo.get("properties").get("nickname").asText();

            User user = userRepository.findByKakaoId(kakaoId)
                    .orElseGet(() -> User.builder()
                            .kakaoId(kakaoId)
                            .connectedAt(LocalDateTime.now())
                            .build());

            user.setNickname(nickname);
            user.setAccessToken(tokens.getAccess_token());
            user.setRefreshToken(tokens.getRefresh_token());

            User savedUser = userRepository.save(user);

            HttpSession session = request.getSession();
            session.setAttribute("user", savedUser);

            return ResponseEntity.ok("로그인 성공!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("로그인 중 오류 발생: " + e.getMessage());
        }
    }
}