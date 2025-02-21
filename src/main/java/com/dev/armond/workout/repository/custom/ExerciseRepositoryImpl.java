package com.dev.armond.workout.repository.custom;

import com.dev.armond.workout.dto.SimpleExerciseDto;
import com.dev.armond.workout.entity.Exercise;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.dev.armond.workout.entity.q.QExercise.exercise;
import static com.dev.armond.workout.entity.q.QExerciseMuscleCategory.exerciseMuscleCategory;
import static com.dev.armond.workout.entity.q.QMuscleCategory.muscleCategory;

@Repository
@RequiredArgsConstructor
public class ExerciseRepositoryImpl implements ExerciseRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<SimpleExerciseDto> getExercises() {
        return queryFactory.select(
                        Projections.constructor(
                                SimpleExerciseDto.class,
                                exercise.name,
                                exercise.description,
                                muscleCategory.name

                        )
                )
                .from(exercise)
                .join(exercise.exerciseMuscleCategories, exerciseMuscleCategory)
                .join(exerciseMuscleCategory.muscleCategory(), muscleCategory)
                .fetch();
    }
}
