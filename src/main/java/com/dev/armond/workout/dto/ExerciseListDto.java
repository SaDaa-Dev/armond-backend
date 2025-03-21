package com.dev.armond.workout.dto;

public record ExerciseListDto(
        Long id,
        String name,
        String description,
        String muscleCategories
) { }

