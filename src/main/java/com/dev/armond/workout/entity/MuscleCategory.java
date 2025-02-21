package com.dev.armond.workout.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MuscleCategory {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private MuscleCategory parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MuscleCategory> muscleCategories = new ArrayList<>();

    @OneToMany(mappedBy = "muscleCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ExerciseMuscleCategory> exerciseMuscleCategories = new ArrayList<>();
}
