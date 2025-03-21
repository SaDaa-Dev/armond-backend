package com.dev.armond.workout.dto;

public record SetDto(
        Long id,
        Integer setNumber,
        Double weight,
        int reps,
        boolean completed
) { }
