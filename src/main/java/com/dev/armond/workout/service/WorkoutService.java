package com.dev.armond.workout.service;

import com.dev.armond.workout.dto.common.ExerciseRecordDto;
import com.dev.armond.workout.dto.workout.QuickWorkoutCompleteDto;

import java.util.List;

public interface WorkoutService {
    void saveQuickWorkout(List<ExerciseRecordDto> exerciseRecords);
    void saveQuickWorkoutWithSave(QuickWorkoutCompleteDto request);
}
