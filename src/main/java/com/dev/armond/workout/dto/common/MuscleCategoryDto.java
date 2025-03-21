package com.dev.armond.workout.dto.common;

import jakarta.annotation.Nullable;

public record MuscleCategoryDto(
        Long id,
        String name,
        String description,

        @Nullable
        MuscleCategoryDto parent
) {
}
