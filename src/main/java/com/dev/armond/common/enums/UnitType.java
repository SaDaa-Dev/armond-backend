package com.dev.armond.common.enums;

import lombok.Getter;

@Getter
public enum UnitType {

    GRAM("g", 1.0),          // 기본 단위: g
    MILLILITER("ml", 1.0),   // 액체 단위: 1ml = 1g (기본 밀도 기준)
    PIECE("piece", 200.0),   // 개당 기본값: 200g (기본값, 재료에 따라 달라질 수 있음)
    CUP("cup", 240.0),       // 1컵 = 240g (기본 밀도 기준)
    TABLESPOON("tbsp", 15.0),// 1 테이블스푼 = 15g
    TEASPOON("tsp", 5.0);    // 1 티스푼 = 5g

    private final String unitName;   // 단위 이름 (표기용)
    private final double gramFactor; // g로 변환하는 계수

    // 생성자
    UnitType(String unitName, double gramFactor) {
        this.unitName = unitName;
        this.gramFactor = gramFactor;
    }

    // 특정 양을 g로 변환
    public double toGrams(double quantity) {
        return quantity * gramFactor;
    }
}