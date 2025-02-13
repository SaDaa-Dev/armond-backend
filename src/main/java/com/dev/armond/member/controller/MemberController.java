package com.dev.armond.member.controller;

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
    public ResponseEntity<Member> createUser(@RequestBody SignUpRequest request) {
        Member registeredUser = userService.registerUser(request);
        log.info("사용자 등록 완료 : {}", registeredUser.toString());
        return ResponseEntity.ok(registeredUser);
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
