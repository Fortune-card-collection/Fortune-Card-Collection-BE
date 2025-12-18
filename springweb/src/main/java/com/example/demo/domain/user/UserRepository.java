package com.example.demo.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 소셜 로그인 핵심 기능: 카카오 ID로 가입된 유저 찾기
    // Optional을 사용하여 유저가 없을 경우(null)를 안전하게 처리합니다.
    Optional<User> findByKakaoId(Long kakaoId);
}