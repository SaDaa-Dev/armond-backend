package com.dev.armond.workout.service;

import com.dev.armond.workout.dto.SimpleExerciseDto;

import java.util.List;

public interface ExerciseService {
    List<SimpleExerciseDto> getExerciseList();
}
