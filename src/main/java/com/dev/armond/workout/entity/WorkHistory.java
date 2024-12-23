package com.dev.armond.workout.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class WorkHistory {
    @Id
    @GeneratedValue
    private Long id;
}
