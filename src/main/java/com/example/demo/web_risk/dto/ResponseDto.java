package com.example.demo.web_risk.dto;

public class ResponseDto {
    private Score[] scores;

    public ResponseDto(Score[] scores) {
        this.scores = scores;
    }

    public ResponseDto() {
    }

    public Score[] getScores() {
        return scores;
    }

    public void setScores(Score[] scores) {
        this.scores = scores;
    }
}
