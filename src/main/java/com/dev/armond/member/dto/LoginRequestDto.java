package com.dev.armond.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotBlank(message = "전화번호는 필수입니다")
    private String memberName;
    
    @NotBlank(message = "비밀번호는 필수입니다")
    private String password;
}

