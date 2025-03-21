package com.dev.armond.workout.dto.common;

import java.util.List;

public record ExerciseDto (
        Long id,
        String name,
        String description,
        Integer orderIdx,
        List<MuscleCategoryDto> muscleCategories
){ }
