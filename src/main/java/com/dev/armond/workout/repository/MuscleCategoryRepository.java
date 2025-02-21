package com.dev.armond.workout.repository;

import com.dev.armond.workout.entity.MuscleCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MuscleCategoryRepository extends JpaRepository<MuscleCategory, Long> {
}
