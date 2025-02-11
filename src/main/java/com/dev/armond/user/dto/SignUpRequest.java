package com.dev.armond.user.dto;

import com.dev.armond.common.enums.Gender;

public record SignUpRequest(
        String name,
        String nickName,
        String email,
        String password,
        Gender gender,
        Double height,
        Double weight,
        int goalCalories
) {}
