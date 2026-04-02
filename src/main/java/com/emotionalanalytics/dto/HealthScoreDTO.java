package com.emotionalanalytics.dto;

import lombok.Value;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class HealthScoreDTO {
    UUID repoId;
    Double healthScore;
    Integer totalCommits;
    Integer positiveCount;
    Integer negativeCount ;
    Integer neutralCount;
    LocalDateTime calculatedAt;
}
