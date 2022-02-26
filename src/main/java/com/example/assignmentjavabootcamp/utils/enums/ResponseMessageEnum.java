package com.example.assignmentjavabootcamp.utils.enums;

import lombok.Getter;

public enum ResponseMessageEnum {
    SUCCESS("Success"),
    FAIL("Fail");

    @Getter
    private String message;

    ResponseMessageEnum(String message) {
        this.message = message;
    }
}
