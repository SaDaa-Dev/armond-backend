package com.dev.armond.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private String grantType; // "Bearer"
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;

    // 사용자 정보
    private MemberInfo memberInfo;
}
