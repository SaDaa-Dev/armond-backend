package com.dev.armond.food.controller;

import com.dev.armond.common.reponse.ApiResponse;
import com.dev.armond.food.domain.Food;
import com.dev.armond.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
@Slf4j
public class FoodController {
    private final FoodService foodService;

    @PostMapping
    public ResponseEntity<ApiResponse<Food>> insertFood(@Validated @RequestBody Food foodDto) {
        try {
            Food food = foodService.insertFood(foodDto);
            return ResponseEntity.ok(ApiResponse.success("Insert Food Success", food));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.error("Insert Food Fail By Error", e.getMessage()));
        }
    }
}
