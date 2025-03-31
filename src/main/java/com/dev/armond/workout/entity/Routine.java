package com.dev.armond.workout.entity;

import com.dev.armond.common.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Routine extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private String description;

    private boolean isCommon;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoutineExercise> routineExercises = new ArrayList<>();

    @Builder
    public Routine(String name, String description) {
        this.name = name;
        this.description = description;
    }
}