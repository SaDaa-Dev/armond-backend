package com.dev.armond.workout.repository;

import com.dev.armond.workout.entity.RoutineExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineExerciseRepository extends JpaRepository<RoutineExercise, Long> {
}
