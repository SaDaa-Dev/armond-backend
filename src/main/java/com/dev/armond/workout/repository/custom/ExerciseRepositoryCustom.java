package com.dev.armond.workout.repository.custom;

import com.dev.armond.workout.dto.SimpleExerciseDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepositoryCustom {
    List<SimpleExerciseDto> getExercises();
}
