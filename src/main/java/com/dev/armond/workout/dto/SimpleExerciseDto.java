package com.dev.armond.workout.dto;

public record SimpleExerciseDto(
        Long id,
        String name,
        String description,
        String muscleCategories
) {
}
