package com.dev.armond.workout.service.impl;

import com.dev.armond.workout.dto.common.RoutineDto;
import com.dev.armond.workout.dto.workout.SaveRoutineDto;
import com.dev.armond.workout.entity.Exercise;
import com.dev.armond.workout.entity.Routine;
import com.dev.armond.workout.entity.RoutineExercise;
import com.dev.armond.workout.repository.ExerciseRepository;
import com.dev.armond.workout.repository.RoutineRepository;
import com.dev.armond.workout.service.RoutineService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dev.armond.workout.entity.q.QRoutine.routine;

@Service
@RequiredArgsConstructor
public class RoutineServiceImpl implements RoutineService {
    private final RoutineRepository routineRepository;
    private final ExerciseRepository exerciseRepository;
    private final JPAQueryFactory queryFactory;
    @Override
    public SaveRoutineDto createRoutine(com.dev.armond.workout.dto.workout.SaveRoutineDto routineDto) {
        Routine routine = Routine.builder()
                .name(routineDto.name())
                .description(routineDto.description())
                .build();

        List<Exercise> exercises = exerciseRepository.findAllById(routineDto.exerciseIds());

        if (exercises.size() != routineDto.exerciseIds().size()) {
            throw new IllegalArgumentException("몇 개의 운동을 찾지 못했습니다.") ;
        }

        int orderIdx = 1;
        for (Exercise exercise : exercises) {
            RoutineExercise.builder()
                    .routine(routine)
                    .exercise(exercise)
                    .orderIdx(orderIdx++)
                    .build();
        }

        Routine savedRoutine = routineRepository.save(routine);

        return new SaveRoutineDto(
                savedRoutine.getId(),
                savedRoutine.getName(),
                savedRoutine.getDescription(),
                exercises.stream().map(Exercise::getId).toList()
        );
    }

    @Override
    public Routine getRoutine(Long id) {
        return null;
    }

    @Override
    public void deleteRoutine(Long id) {
        routineRepository.deleteById(id);
    }

    @Override
    public SaveRoutineDto updateRoutine(Long id, SaveRoutineDto routineDto) {
        return null;
    }

    @Override
    public List<RoutineDto> getRoutines(Long memberId) {
        queryFactory.select(
                        routine
                )
                .from(routine)
                .where(
                        routine.createBy.eq(memberId) // 내가 등록한 루틴
                                .or(routine.isCommon.eq(true))
                )
                .fetch();
        return null;
    }
}
