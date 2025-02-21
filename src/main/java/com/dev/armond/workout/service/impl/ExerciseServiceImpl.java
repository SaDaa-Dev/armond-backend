package com.dev.armond.workout.service.impl;

import com.dev.armond.workout.dto.SimpleExerciseDto;
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
    public List<SimpleExerciseDto> getExerciseList() {
        return exerciseRepository.getExercises();
    }
}
