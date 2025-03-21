package com.dev.armond.workout.controller;

import com.dev.armond.common.reponse.ApiResponse;
import com.dev.armond.workout.dto.ExerciseListDto;
import com.dev.armond.workout.service.ExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/exercises")
@Tag(name = "Exercises API", description = "운동 관리 API")
public class ExerciseController {
    private final ExerciseService exerciseService;
    @Operation(summary = "운동 목록 조회", description = "전체 운동 목록을 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ExerciseListDto>>> getExercises() {
        List<ExerciseListDto> exerciseList = exerciseService.getExerciseList();
        return ResponseEntity.ok(ApiResponse.success("getExercises", exerciseList));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ExerciseListDto>> saveExercise(@RequestBody ExerciseListDto exercise) {
        return ResponseEntity.ok(ApiResponse.success("Save Exercise Success", exercise));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Integer>> updateExercise(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Delete Exercise Success", null));
    }
}
