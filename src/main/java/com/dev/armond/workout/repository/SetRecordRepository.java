package com.dev.armond.workout.repository;

import com.dev.armond.workout.entity.SetRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetRecordRepository extends JpaRepository<SetRecord, Long> {
}
