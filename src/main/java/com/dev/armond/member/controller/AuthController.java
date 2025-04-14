package com.dev.armond.member.controller;

import com.dev.armond.common.reponse.ApiResponse;
import com.dev.armond.member.dto.*;
import com.dev.armond.member.entity.Member;
import com.dev.armond.member.repository.MemberRepository;
import com.dev.armond.member.service.MemberService;
import com.dev.armond.member.service.impl.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<TokenResponse>> signup(@RequestBody SignUpDto request) {
        Member member = memberService.signupMember(request);
        TokenResponse tokens = authService.createTokenForMember(member);
        return ResponseEntity.ok(ApiResponse.success("회원가입 완료", tokens));
    }

    @Operation(summary = "로그인", description = "전화번호/비밀번호로 로그인")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody LoginDto request) {
        TokenResponse tokens = authService.login(request.phoneNumber(), request.password());
        return ResponseEntity.ok(ApiResponse.success("로그인 완료", tokens));
    }

    @Operation(summary = "토큰 재발급", description = "RefreshToken을 사용해 새로운 AccessToken을 발급")
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenResponse>> reissue(@RequestBody RefreshTokenRequest request) {
        TokenResponse token = authService.reissue(request.refreshToken());
        return ResponseEntity.ok(ApiResponse.success("토큰 재발급 완료", token));
    }

    @Operation(summary = "로그아웃", description = "서버에서 RefreshToken을 삭제")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(@AuthenticationPrincipal CustomMemberDetails memberDetails) {
        authService.logout(memberDetails.getMemberId());
        return ResponseEntity.ok(ApiResponse.success("로그아웃", memberDetails.getPhoneNumber()));
    }
}
