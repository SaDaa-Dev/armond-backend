package com.dev.armond.workout.controller;

import com.dev.armond.common.reponse.ApiResponse;
import com.dev.armond.workout.entity.Exercise;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercise")
@Slf4j
public class ExerciseController {
    @GetMapping
    public ResponseEntity<ApiResponse<Exercise>> getExercises(@RequestParam(required = false) Long id) {
        Exercise exercise = new Exercise();
        log.info("운동 조회 요청: id={}", id);
        return ResponseEntity.ok(ApiResponse.success("운동 조회 성공", exercise));
    }
}
