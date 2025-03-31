package com.dev.armond.workout.dto.workout;

import java.util.List;

public record SaveRoutineDto (
        Long id,
        String name,
        String description,
        List<Long> exerciseIds
) {
}
