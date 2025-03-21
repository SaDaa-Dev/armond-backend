package com.dev.armond.workout.dto.workout;

import com.dev.armond.workout.dto.common.ExerciseRecordDto;

import java.util.List;

public record RoutineWorkoutCompleteDto (
        List<ExerciseRecordDto> exerciseRecords,
        String routineName,
        String description,
        boolean workoutMod
){ }
