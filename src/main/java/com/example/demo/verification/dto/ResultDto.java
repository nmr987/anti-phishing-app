package com.example.demo.verification.dto;

public class ResultDto {
    private final boolean toBeBlocked;
    private final String message;

    public ResultDto(boolean toBeBlocked, String message) {
        this.toBeBlocked = toBeBlocked;
        this.message = message;
    }

    public boolean isToBeBlocked() {
        return toBeBlocked;
    }

    public String getMessage() {
        return message;
    }
}
