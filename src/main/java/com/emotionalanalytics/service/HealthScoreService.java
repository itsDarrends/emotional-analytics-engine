package com.emotionalanalytics.service;

import com.emotionalanalytics.dto.HealthScoreDTO;
import com.emotionalanalytics.entities.Commit;
import com.emotionalanalytics.entities.HealthScore;
import com.emotionalanalytics.entities.Repo;
import com.emotionalanalytics.repositories.CommitRepository;
import com.emotionalanalytics.repositories.HealthScoreRepository;
import com.emotionalanalytics.repositories.RepoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HealthScoreService {
    private final CommitRepository commitRepository;
    private final HealthScoreRepository healthScoreRepository;
    private final RepoRepository repoRepository;

    public HealthScoreDTO calculateHealthScore(UUID repoId) {
        Repo repo = repoRepository.findById(repoId)
                .orElseThrow(() -> new RuntimeException("Repo Not Found"));

        //getting all commits like "Positive,negative and nutral"
        List<Commit> commits = commitRepository.findByRepoId(repoId);

        long positiveCount = commits.stream()
                .filter(c -> "POSITIVE".equals(c.getSentimentLabel()))
                .count();

        long negativeCount = commits.stream()
                .filter(c -> "NEGATIVE".equals(c.getSentimentLabel()))
                .count();

        long neutralCount = commits.stream()
                .filter(c -> "NEUTRAL".equals(c.getSentimentLabel()))
                .count();

        int totalCommits = commits.size();
        double score = ((positiveCount - negativeCount) / (double) totalCommits) * 50 + 50;

        HealthScore healthScore = new HealthScore();
        healthScore.setRepo(repo);
        healthScore.setHealthScore(score);
        healthScore.setTotalCommits(totalCommits);
        healthScore.setPositiveCount((int) positiveCount);
        healthScore.setNegativeCount((int) negativeCount);
        healthScore.setNeutralCount((int) neutralCount);
        healthScoreRepository.save(healthScore);

        return new HealthScoreDTO(
                repoId,
                score,
                totalCommits,
                (int) positiveCount,
                (int) negativeCount,
                (int) neutralCount,
                healthScore.getCalculatedAt()
        );
    }
}
