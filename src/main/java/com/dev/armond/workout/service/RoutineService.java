package com.dev.armond.workout.service;

import com.dev.armond.workout.dto.common.RoutineDto;
import com.dev.armond.workout.dto.workout.SaveRoutineDto;
import com.dev.armond.workout.entity.Routine;

import java.util.List;

public interface RoutineService {
    SaveRoutineDto createRoutine(SaveRoutineDto routineDto);
    Routine getRoutine(Long id);

    void deleteRoutine(Long id);

    SaveRoutineDto updateRoutine(Long id, SaveRoutineDto saveRoutineDto);

    List<RoutineDto> getRoutines(Long memberId);
}
