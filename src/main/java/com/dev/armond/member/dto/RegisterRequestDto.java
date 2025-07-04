package com.dev.armond.member.dto;

import com.dev.armond.common.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequestDto(
        @NotBlank(message = "전화번호는 필수 입력 값입니다.")
        @Pattern(regexp = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$",
                message = "유효한 휴대폰 번호 형식이 아닙니다. (예: 01012345678)")
        String phoneNumber,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$",
                message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다.")
        String password,

        String name,
        String nickName,
        Gender gender,
        Double height,
        Double weight,
        int goalCalories
) {}
