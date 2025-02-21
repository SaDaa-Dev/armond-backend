package com.dev.armond.exercise;

import com.dev.armond.workout.entity.Exercise;
import com.dev.armond.workout.entity.ExerciseMuscleCategory;
import com.dev.armond.workout.entity.MuscleCategory;
import com.dev.armond.workout.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ExerciseTest {
    private static final Logger log = LoggerFactory.getLogger(ExerciseTest.class);

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private MuscleCategoryRepository exerciseTypeRepository;

    @Autowired
    private ExerciseMuscleCategoryRepository exerciseExerciseTypeRepository;

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private RoutineExerciseRepository routineExerciseRepository;

    @Autowired
    private ExerciseRecordRepository exerciseRecordRepository;

    @Autowired
    private SetRecordRepository setRecordRepository;


    @Test
    @DisplayName("Exercise와 ExerciseType, 그리고 중간 엔티티의 저장 및 조회 테스트")

    public void testExerciseAndExerciseType() {
        // ExerciseType 생성 (예: Chest, Triceps)
        MuscleCategory chest = MuscleCategory.builder()
                .name("Chest")
                .build();
        MuscleCategory triceps = MuscleCategory.builder()
                .name("Triceps")
                .build();

        exerciseTypeRepository.saveAll(Arrays.asList(chest, triceps));

        // Exercise 생성 (예: Bench Press)
        Exercise benchPress = Exercise.builder()
                .name("Bench Press")
                .description("Bench press exercise for chest and triceps")
                .build();
        exerciseRepository.save(benchPress);

        // Exercise와 ExerciseType 간 관계 생성
        ExerciseMuscleCategory eet1 = ExerciseMuscleCategory.builder()
                .exercise(benchPress)
                .muscleCategory(triceps)
                .build();
        ExerciseMuscleCategory eet2 = ExerciseMuscleCategory.builder()
                .exercise(benchPress)
                .muscleCategory(chest)
                .build();
        exerciseExerciseTypeRepository.saveAll(Arrays.asList(eet1, eet2));

        // 저장 후 Exercise를 조회하여 확인
        Exercise savedExercise = exerciseRepository.findById(benchPress.getId()).orElse(null);
        assertThat(savedExercise).isNotNull();
        log.info("캬캬캬ㅑ캬캬");
    }

    @Test
    public void testSaveExercise() {
        Exercise exercise = Exercise.builder()
                .name("Bench Press")
                .description("Chest and triceps exercise")
                .build();
        Exercise saved = exerciseRepository.save(exercise);
        assertThat(saved.getId()).isNotNull();
    }


}
