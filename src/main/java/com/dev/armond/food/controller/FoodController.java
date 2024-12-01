package com.dev.armond.food.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class FoodController {

    @GetMapping("/null")
    public String getNullPointer() {
        throw new NullPointerException("Nullpointer Exception 발생!");
    }

}
