package com.dev.armond.workout.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetRecord {
    @Id @GeneratedValue
    private Long id;

    private int setNumber;
    private double weight;
    private int reps;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_record_id", nullable = false)
    private ExerciseRecord exerciseRecord;

    @Builder
    public SetRecord(Integer setNumber, Double weight, Integer reps, Integer restTimeInSeconds) {
        this.setNumber = setNumber;
        this.weight = weight;
        this.reps = reps;
    }

    public void setExerciseRecord(ExerciseRecord exerciseRecord) {
        this.exerciseRecord = exerciseRecord;
    }
}
