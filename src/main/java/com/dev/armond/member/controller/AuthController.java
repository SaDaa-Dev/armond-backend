package com.dev.armond.member.controller;

import com.dev.armond.common.reponse.ApiResponse;
import com.dev.armond.member.dto.LoginRequestDto;
import com.dev.armond.member.dto.RegisterRequestDto;
import com.dev.armond.member.dto.TokenDto;
import com.dev.armond.member.service.MemberService;
import com.dev.armond.member.service.impl.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        memberService.registerMember(registerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 성공적으로 완료되었습니다.");
    }

    @Operation(summary = "로그인", description = "전화번호/비밀번호로 로그인")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenDto>> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        TokenDto tokenDto = authService.login(loginRequestDto);
        return ResponseEntity.ok(ApiResponse.success("로그인 완료", tokenDto));
    }

    @Operation(summary = "토큰 재발급", description = "RefreshToken을 사용해 새로운 AccessToken을 발급")
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenDto>> reissue(
            @RequestHeader("Authorization") String accessToken,
            @RequestHeader("Refresh-Token") String refreshToken
    ) {
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }
        TokenDto tokenDto = authService.reissue(accessToken, refreshToken);
        return ResponseEntity.ok(ApiResponse.success("RefreshToken 재발행 완료", tokenDto));
    }

    @Operation(summary = "로그아웃", description = "서버에서 RefreshToken을 삭제")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(
            @RequestHeader("Authorization") String accessToken
    ) {
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }
        authService.logout(accessToken);
        // 로그아웃 이름 반환 필요시 이름 받아오자
        return ResponseEntity.ok(ApiResponse.success("로그아웃 성공", null));
    }
}
