package com.emotionalanalytics.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "health_scores")
@Data
public class HealthScore {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "repo_id")
    private Repo repo;

    private Double healthScore;
    private Integer totalCommits;
    private Integer positiveCount;
    private Integer negativeCount;
    private Integer neutralCount;
    private LocalDateTime calculatedAt;

    @PrePersist
    protected void onCreate() {
        calculatedAt = LocalDateTime.now();
    }
}