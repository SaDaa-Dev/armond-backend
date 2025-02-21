package com.dev.armond.workout.repository;

import com.dev.armond.workout.entity.Exercise;
import com.dev.armond.workout.repository.custom.ExerciseRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long>, ExerciseRepositoryCustom {

}

