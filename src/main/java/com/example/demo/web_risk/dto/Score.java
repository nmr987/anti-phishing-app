package com.example.demo.web_risk.dto;

import com.example.demo.web_risk.enums.ConfidenceLevel;
import com.example.demo.web_risk.enums.ThreatType;

import java.util.Set;

public class Score {
    private ThreatType threatType;
    private ConfidenceLevel confidenceLevel;

    private static final Set<ConfidenceLevel> HIGH_CONFIDENCE_LEVELS = Set.of(ConfidenceLevel.HIGH, ConfidenceLevel.HIGHER,
            ConfidenceLevel.VERY_HIGH, ConfidenceLevel.EXTREMELY_HIGH);

    public boolean isHighConfidenceLevel(){
        return HIGH_CONFIDENCE_LEVELS.contains(confidenceLevel);
    }
}
