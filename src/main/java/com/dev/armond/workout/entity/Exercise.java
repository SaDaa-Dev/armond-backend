package com.dev.armond.workout.entity;

import com.dev.armond.common.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Exercise extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    private Integer orderIdx;

    @ManyToOne
    @JoinColumn(name = "routine_id")
    private Routine routine;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<ExerciseMuscleCategory> exerciseMuscleCategories = new HashSet<>();
}
