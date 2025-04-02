package com.dev.armond.member.dto;

import com.dev.armond.common.enums.Gender;

public record SignUpDto(
        String name,
        String nickName,
        String phoneNumber,
        String password,
        Gender gender,
        Double height,
        Double weight,
        int goalCalories
) {}
