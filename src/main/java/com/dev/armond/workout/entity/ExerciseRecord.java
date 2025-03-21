package com.dev.armond.workout.entity;

import com.dev.armond.common.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExerciseRecord extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @OneToMany(mappedBy = "exerciseRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SetRecord> setRecords = new ArrayList<>();

    @Builder
    public ExerciseRecord(Exercise exercise) {
        this.exercise = exercise;
    }

    public void setSetRecords(List<SetRecord> setRecords) {
        this.setRecords.clear();
        this.setRecords.addAll(setRecords);
        setRecords.forEach(set -> set.setExerciseRecord(this));
    }
}
