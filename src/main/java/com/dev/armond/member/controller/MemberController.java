package com.dev.armond.member.controller;

import com.dev.armond.common.reponse.ApiResponse;
import com.dev.armond.member.dto.SignUpRequest;
import com.dev.armond.member.entity.Member;
import com.dev.armond.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> createUser(@RequestBody SignUpRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok(ApiResponse.success("사용자 등록 완료", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getUser(@PathVariable Long id) {
        Optional<Member> findUser = userService.getUser(id);
        if (findUser.isEmpty()) {
            throw new RuntimeException("우우");
        }

        return ResponseEntity.ok(findUser.get());
    }
}
