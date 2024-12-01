package com.dev.armond.food.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Food {
    @Id @GeneratedValue
    private Long id;

    private String name;

    private int value;
}
