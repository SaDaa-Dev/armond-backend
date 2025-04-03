package com.dev.armond.member.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {

}

