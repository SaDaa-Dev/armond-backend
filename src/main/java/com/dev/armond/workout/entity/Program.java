package com.dev.armond.workout.entity;

import com.dev.armond.common.baseentity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Program extends BaseEntity {
    @Id @GeneratedValue
    private Long id;

    private Long userId;

    private String name;

    // 1(기록형), 2(주간형), 3(블록형)
    private int type;

    private String description;

    private boolean isActive;
}
