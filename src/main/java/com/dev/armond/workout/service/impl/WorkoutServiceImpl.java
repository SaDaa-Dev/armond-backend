package com.dev.armond.workout.service.impl;

import com.dev.armond.workout.dto.common.ExerciseRecordDto;
import com.dev.armond.workout.dto.workout.QuickWorkoutCompleteDto;
import com.dev.armond.workout.entity.Exercise;
import com.dev.armond.workout.entity.ExerciseRecord;
import com.dev.armond.workout.repository.ExerciseRecordRepository;
import com.dev.armond.workout.repository.ExerciseRepository;
import com.dev.armond.workout.service.WorkoutService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {
    private final ExerciseRecordRepository exerciseRecordRepository;
    private final ExerciseRepository exerciseRepository;
    @Override
    public void saveQuickWorkout(List<ExerciseRecordDto> exerciseRecords) {
        for (ExerciseRecordDto exerciseDto : exerciseRecords) {
            Exercise exercise = exerciseRepository.findById(exerciseDto.exercise().id())
                    .orElseThrow(() -> new EntityNotFoundException("Exercise Not Found"));

            ExerciseRecord exerciseRecord = exerciseDto.toEntity(exercise);
            exerciseRecordRepository.save(exerciseRecord);
        }
    }

    @Override
    public void saveQuickWorkoutWithSave(QuickWorkoutCompleteDto request) {
        // TODO Routine 내역으로 저장
    }
}
