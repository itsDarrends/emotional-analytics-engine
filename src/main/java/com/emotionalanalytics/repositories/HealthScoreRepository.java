package com.emotionalanalytics.repositories;

import com.emotionalanalytics.entities.HealthScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HealthScoreRepository extends JpaRepository<HealthScore, UUID> {
}
