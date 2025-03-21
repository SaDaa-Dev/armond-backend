package com.dev.armond.workout.dto.common;

import com.dev.armond.workout.entity.ExerciseRecord;
import com.dev.armond.workout.entity.SetRecord;

public record SetRecordDto(
        Long id,
        Integer setNumber,
        double weight,
        Integer reps
) {
    public SetRecord toEntity(ExerciseRecord exerciseRecord) {
        SetRecord setRecord = SetRecord.builder()
                .setNumber(setNumber)
                .weight(weight)
                .reps(reps)
                .build();

        setRecord.setExerciseRecord(exerciseRecord);
        return setRecord;
    }
}
