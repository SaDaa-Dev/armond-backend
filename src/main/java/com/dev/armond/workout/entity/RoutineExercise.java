package com.dev.armond.workout.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutineExercise {
    @Id @GeneratedValue
    private Long id;

    private Integer orderIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id", nullable = false)
    private Routine routine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Builder
    public RoutineExercise(Routine routine, Exercise exercise, int orderIdx) {
        this.routine = routine;
        this.exercise = exercise;
        this.orderIdx = orderIdx;
    }
}
