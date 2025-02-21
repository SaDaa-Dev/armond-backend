    package com.dev.armond.workout.controller;

    import com.dev.armond.common.reponse.ApiResponse;
    import com.dev.armond.workout.dto.SimpleExerciseDto;
    import com.dev.armond.workout.service.ExerciseService;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import java.util.List;

    @RestController
    @Slf4j
    @RequiredArgsConstructor
    @RequestMapping("/exercises")
    public class ExerciseController {
        private final ExerciseService exerciseService;
        @GetMapping
        public ResponseEntity<ApiResponse<List<SimpleExerciseDto>>> getExercises() {
            List<SimpleExerciseDto> exerciseList = exerciseService.getExerciseList();
            return ResponseEntity.ok(ApiResponse.success("getExercises", exerciseList));
        }

    }
