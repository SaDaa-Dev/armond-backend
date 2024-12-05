package com.dev.armond.food.service.impl;

import com.dev.armond.food.domain.Food;
import com.dev.armond.food.repository.FoodRepository;
import com.dev.armond.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;

    @Override
    public Food insertFood(Food food) {
        return foodRepository.save(food);
    }
}
