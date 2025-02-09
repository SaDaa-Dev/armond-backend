package com.dev.armond.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @PostMapping("/users")
    public ResponseEntity postTest(@RequestParam("id") Long id) {
        log.info("id : {}", id);
        return ResponseEntity.ok().body(id);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getTest(@PathVariable("id") Long id) {
        log.info("id : {}", id);
        return ResponseEntity.ok().body(id);
    }
}
