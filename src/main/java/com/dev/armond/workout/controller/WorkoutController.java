package com.dev.armond.workout.controller;

import com.dev.armond.common.enums.WorkoutMod;
import com.dev.armond.common.reponse.ApiResponse;
import com.dev.armond.workout.dto.workout.QuickWorkoutCompleteDto;
import com.dev.armond.workout.service.WorkoutService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workouts")
@RequiredArgsConstructor
@Slf4j
public class WorkoutController {
    private final WorkoutService workoutService;

    @PostMapping("/complete")
    public ResponseEntity<ApiResponse<String>> completeWorkout(
            @RequestBody QuickWorkoutCompleteDto request
    ) {
        if (request.workoutMod().equals(WorkoutMod.QUICK)) {
            workoutService.saveQuickWorkout(request.exerciseRecords());
        }
        return ResponseEntity.ok(ApiResponse.success("Quick workout completed successfully.", null));
    }
}
