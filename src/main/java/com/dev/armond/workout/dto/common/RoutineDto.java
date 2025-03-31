package com.dev.armond.workout.dto.common;

import java.util.List;

public record RoutineDto(
        String name,
        String description,
        List<Long> exerciseIds,
        int order
) {
}
