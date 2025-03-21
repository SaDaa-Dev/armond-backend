package com.dev.armond.workout.repository.custom;

import com.dev.armond.workout.dto.ExerciseListDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepositoryCustom {
    List<ExerciseListDto> getExercises();
}
