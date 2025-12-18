// domain/user/User.java
package com.example.demo.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate // 변경된 필드만 Update 쿼리 날림
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "kakao_usr_id", unique = true, nullable = false)
    private Long kakaoId; // 카카오 고유 ID

    private String nickname;

    // 토큰 정보 (ERD 반영)
    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    // 연결 시간
    @Column(name = "connected_at")
    private LocalDateTime connectedAt;

    // --- 추후 입력 받을 정보 (Nullable) ---

    @Column(name = "birth_date", length = 8)
    private String birthDate; // YYYYMMDD

    @Column(name = "birth_time")
    private String birthTime; // 자시, 축시 등

    @Enumerated(EnumType.STRING)
    @Column(name = "lunar_type")
    private LunarType lunarType; // solar, lunar, lunar_leap

    @Enumerated(EnumType.STRING)
    private Gender gender; // 남성, 여성

    // 편의 메서드: 기본 정보 업데이트
    public void updateBasicInfo(String birthDate, String birthTime, LunarType lunarType, Gender gender) {
        this.birthDate = birthDate;
        this.birthTime = birthTime;
        this.lunarType = lunarType;
        this.gender = gender;
    }
}