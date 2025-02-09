package com.dev.armond.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping
    public ResponseEntity getUser(@RequestParam(name = "id") String email) {
        return ResponseEntity.ok().body("hi");
    }
}
