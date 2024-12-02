package com.dev.armond.food.entity;

import com.dev.armond.common.enums.UnitType;
import com.dev.armond.meal.entity.Meal;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
@ToString(exclude = "meal")
public class Food {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private double quantity;
    @Enumerated(EnumType.STRING)
    private UnitType unitType;
    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    private int calories;
    private double fat;
    private double carbohydrates;
    private double protein;

    // 기타 성분들 (선택)
    private String brandName;
    private double cholesterol;
    private double saturatedFat;
    private double transFat;

    //--------------------------------------------------


    // 단위별 계산
    private static final double CUP_TO_GRAM = 240; // 예: 1컵 = 240g
    private static final double TBSP_TO_GRAM = 15; // 예: 1테이블스푼 = 15g
    private static final double TSP_TO_GRAM = 5;  // 예: 1티스푼 = 5g

    @Builder(builderMethodName = "createFood")
    public Food(String name, double quantity, UnitType unitType, double fat, double carbohydrates, double protein,
                String brandName, double cholesterol, double saturatedFat, double transFat, Meal meal) {

        // 필수값 유효성 검사
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("음식 이름은 필수 값입니다.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("수량(quantity)은 0보다 커야 합니다.");
        }
        if (fat < 0 || carbohydrates < 0 || protein < 0) {
            throw new IllegalArgumentException("영양소 값은 음수가 될 수 없습니다.");
        }

        // 필수값
        this.name = name;
        this.quantity = quantity;
        this.unitType = unitType;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
        // 선택값
        this.brandName = brandName;
        this.cholesterol = cholesterol;
        this.saturatedFat = saturatedFat;
        this.transFat = transFat;
        this.meal = meal;

        this.calories = calculateCalories();
    }

    // 칼로리 계산 로직
    private int calculateCalories() {
        double weightInGrams = convertToGrams(quantity, unitType);
        log.debug("Weight in grams: {}", weightInGrams);
        log.debug("Fat: {}, Carbohydrates: {}, Protein: {}", fat, carbohydrates, protein);
        return (int) Math.round(weightInGrams / 100 * (fat * 9 + carbohydrates * 4 + protein * 4));
    }

    private double convertToGrams(double quantity, UnitType unitType) {
        double result;
        switch (unitType) {
            case GRAM:
                result = quantity;
                break;
            case MILLILITER:
                result = quantity;
                break;
            case PIECE:
                result = quantity * 200; // 기본값: 1개 = 200g
                break;
            case CUP:
                result = quantity * CUP_TO_GRAM;
                break;
            case TABLESPOON:
                result = quantity * TBSP_TO_GRAM;
                break;
            case TEASPOON:
                result = quantity * TSP_TO_GRAM;
                break;
            default:
                throw new IllegalArgumentException("Unsupported unit type: " + unitType);
        }
        log.debug("Convert to grams - Quantity: {}, UnitType: {}, Result: {}", quantity, unitType, result);
        return result;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }
}


