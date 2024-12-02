package com.dev.armond.food.repository;

import com.dev.armond.common.enums.UnitType;
import com.dev.armond.food.entity.Food;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FoodRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(FoodRepositoryTest.class);
    @Test
    void addTset() throws Exception {
        //given
        Food food = Food.createFood()
                .name("닭찌찌")
                .brandName("하림")
                .quantity(200)
                .unitType(UnitType.GRAM)
                .fat(3.5)
                .carbohydrates(0)
                .protein(20)
                .build();

        int calories = food.getCalories();
        logger.debug("여기요 : {}", calories);
        logger.debug("Food Details: {}", food);
        //when

        //then
    }

}