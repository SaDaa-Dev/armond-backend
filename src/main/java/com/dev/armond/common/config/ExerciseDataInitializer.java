package com.dev.armond.common.config;

import com.dev.armond.workout.entity.Exercise;
import com.dev.armond.workout.entity.ExerciseMuscleCategory;
import com.dev.armond.workout.entity.MuscleCategory;
import com.dev.armond.workout.repository.ExerciseRepository;
import com.dev.armond.workout.repository.MuscleCategoryRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Profile({"local", "dev"})
@Slf4j
public class ExerciseDataInitializer {
    @Bean
    public CommandLineRunner initPresetExercises(ExerciseRepository exerciseRepository, MuscleCategoryRepository muscleCategoryRepository) {
        return args -> {
            if (exerciseRepository.findAll().size() == 0) {
                // 필요한 MuscleCategory들을 생성하고 Map에 저장 (키: 카테고리 이름)
                Map<String, MuscleCategory> categoryMap = new HashMap<>();

                // 상위 카테고리 (대분류)
                MuscleCategory upperBody = MuscleCategory.builder()
                        .name("Upper Body")
                        .build();
                muscleCategoryRepository.save(upperBody);
                categoryMap.put("Upper Body", upperBody);

                MuscleCategory lowerBody = MuscleCategory.builder()
                        .name("Lower Body")
                        .build();
                muscleCategoryRepository.save(lowerBody);
                categoryMap.put("Lower Body", lowerBody);

                MuscleCategory core = MuscleCategory.builder()
                        .name("Core")
                        .build();
                muscleCategoryRepository.save(core);
                categoryMap.put("Core", core);

                // 중분류 (예시)
                MuscleCategory pectoralis = MuscleCategory.builder()
                        .name("Pectoralis")
                        .parent(upperBody)
                        .build();
                muscleCategoryRepository.save(pectoralis);
                categoryMap.put("Pectoralis", pectoralis);

                MuscleCategory arms = MuscleCategory.builder()
                        .name("Arms")
                        .parent(upperBody)
                        .build();
                muscleCategoryRepository.save(arms);
                categoryMap.put("Arms", arms);

                // 추가로 세분화된 카테고리 (예: 가슴 세분화)
                MuscleCategory upperPectoralisMajor = MuscleCategory.builder()
                        .name("Upper Pectoralis Major")
                        .parent(pectoralis)
                        .build();
                muscleCategoryRepository.save(upperPectoralisMajor);
                categoryMap.put("Upper Pectoralis Major", upperPectoralisMajor);

                // 하체 예시 카테고리
                MuscleCategory quads = MuscleCategory.builder()
                        .name("Quadriceps")
                        .parent(lowerBody)
                        .build();
                muscleCategoryRepository.save(quads);
                categoryMap.put("Quadriceps", quads);



                // 프리셋 운동 데이터를 헬퍼 객체(PresetExercise)로 정의
                List<PresetExercise> presetExercises = List.of(
                        new PresetExercise("Bench Press", "가슴과 삼두를 동시에 사용하는 복합 운동", 1, List.of("Pectoralis", "Arms")),
                        new PresetExercise("Squat", "하체 전반을 사용하는 운동", 2, List.of("Lower Body", "Quadriceps")),
                        new PresetExercise("Plank", "코어 안정성 및 근지구력을 높이는 운동", 3, List.of("Core"))
                        // 필요한 프리셋을 추가
                );

                // 3. 프리셋 운동을 순회하며 저장 (연관된 MuscleCategory 연결)
                presetExercises.forEach(pe -> {
                    Exercise exercise = Exercise.builder()
                            .name(pe.getName())
                            .description(pe.getDescription())
                            .orderIdx(pe.getOrderIdx())
                            .build();

                    pe.getMuscleCategories().forEach(catName -> {
                        MuscleCategory category = categoryMap.get(catName);
                        if (category != null) {
                            ExerciseMuscleCategory emc = ExerciseMuscleCategory.builder()
                                    .exercise(exercise)
                                    .muscleCategory(category)
                                    .build();
                            exercise.getExerciseMuscleCategories().add(emc);
                        }
                    });
                    exerciseRepository.save(exercise);
                });
            }
        };
    }

    // 헬퍼 클래스: 프리셋 운동 정보를 담기 위한 단순 POJO
    @Data
    private static class PresetExercise {
        private final String name;
        private final String description;
        private final int orderIdx;
        private final List<String> muscleCategories;
    }
}