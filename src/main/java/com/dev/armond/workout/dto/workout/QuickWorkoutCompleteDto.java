package com.dev.armond.workout.dto.workout;

import com.dev.armond.common.enums.WorkoutMod;
import com.dev.armond.workout.dto.common.ExerciseRecordDto;

import java.util.List;

public record QuickWorkoutCompleteDto(
        List<ExerciseRecordDto> exerciseRecords,
        WorkoutMod workoutMod,
        boolean isSave
){

}
