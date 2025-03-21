package com.dev.armond.workout.service;

import com.dev.armond.workout.dto.ExerciseListDto;

import java.util.List;

public interface ExerciseService {
    List<ExerciseListDto> getExerciseList();

    ExerciseListDto saveExercise(ExerciseListDto exercise);

    void deleteExercise(Long id);


}
