package com.example.demo.api;

import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import com.example.demo.dto.UserUpdateRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<?> getMyInfo(HttpSession session) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        User currentUser = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        return ResponseEntity.ok(currentUser);
    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateMyInfo(
            @RequestBody UserUpdateRequest requestDto,
            HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            return ResponseEntity.status(401).body("로그인이 필요합니다.");
        }

        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new RuntimeException("사용자 정보가 없습니다."));

        user.updateBasicInfo(
                requestDto.getBirthDate(),
                requestDto.getBirthTime(),
                requestDto.getLunarType(),
                requestDto.getGender()
        );

        User updatedUser = userRepository.save(user);

        session.setAttribute("user", updatedUser);

        return ResponseEntity.ok(updatedUser);
    }
}