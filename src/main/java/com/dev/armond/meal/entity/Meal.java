package com.dev.armond.meal.entity;

import com.dev.armond.common.baseentity.BaseEntity;
import com.dev.armond.common.enums.MealType;
import com.dev.armond.food.domain.Food;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Meal extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private MealType mealType;

    private int totalCalories;

    // 필요시 cascade, orphanRemoval 사용 (식사 일괄 삭제 기능 만들 경우)
    @OneToMany(mappedBy = "meal")
    private List<Food> foods = new ArrayList<>();

    public void addFood(Food newFood) {
        foods.add(newFood);
        newFood.setMeal(this);
    }

    public void removeFood(Food food) {
        foods.remove(food);
        food.setMeal(null);
    }

}
