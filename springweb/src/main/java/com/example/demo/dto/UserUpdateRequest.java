package com.example.demo.dto;

import com.example.demo.domain.user.Gender;
import com.example.demo.domain.user.LunarType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserUpdateRequest {
    private String birthDate;
    private String birthTime;
    private LunarType lunarType;
    private Gender gender;
}