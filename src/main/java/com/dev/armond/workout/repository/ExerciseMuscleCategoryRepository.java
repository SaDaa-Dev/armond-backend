package com.dev.armond.workout.repository;

import com.dev.armond.workout.entity.ExerciseMuscleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseMuscleCategoryRepository extends JpaRepository<ExerciseMuscleCategory, Long> {
}
