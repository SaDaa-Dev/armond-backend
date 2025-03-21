package com.dev.armond.workout.service.impl;

import com.dev.armond.workout.dto.ExerciseListDto;
import com.dev.armond.workout.repository.ExerciseRepository;
import com.dev.armond.workout.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {
    private final ExerciseRepository exerciseRepository;
    @Override
    public List<ExerciseListDto> getExerciseList() {
        return exerciseRepository.getExercises();
    }

    @Override
    public ExerciseListDto saveExercise(ExerciseListDto exercise) {
        return null;
    }

    @Override
    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }
}