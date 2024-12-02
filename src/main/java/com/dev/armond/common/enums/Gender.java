package com.dev.armond.common.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("남성"), FEMALE("여성");

    private String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }
}
