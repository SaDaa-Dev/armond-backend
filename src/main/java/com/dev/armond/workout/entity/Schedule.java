package com.dev.armond.workout.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Schedule {
    @Id @GeneratedValue
    private Long id;

    private Long programId;

    private LocalDateTime date;


}
