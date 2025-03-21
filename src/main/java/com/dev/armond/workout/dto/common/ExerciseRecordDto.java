package com.dev.armond.workout.dto.common;

import com.dev.armond.workout.entity.Exercise;
import com.dev.armond.workout.entity.ExerciseRecord;
import com.dev.armond.workout.entity.SetRecord;

import java.util.List;

public record ExerciseRecordDto(
        ExerciseDto exercise,
        List<SetRecordDto> sets,
        Long id) {
    // DTO → Entity 변환 메서드
    public ExerciseRecord toEntity(Exercise exerciseEntity) {
        ExerciseRecord exerciseRecord = ExerciseRecord.builder()
                .exercise(exerciseEntity)
                .build();

        List<SetRecord> setRecordEntities = sets.stream()
                .map(dto -> dto.toEntity(exerciseRecord))
                .toList();

        exerciseRecord.setSetRecords(setRecordEntities);

        return exerciseRecord;
    }
}
