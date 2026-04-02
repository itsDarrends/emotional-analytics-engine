package com.emotionalanalytics.service;

import com.emotionalanalytics.entities.Commit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NLPAnalysisService {

    private static final List<String> NEGATIVE_WORDS = List.of(
            "fix", "bug", "broken", "error", "fail", "crash", "stupid",
            "garbage", "hack", "ugly", "wrong", "bad", "terrible", "awful"
    );

    private static final List<String> POSITIVE_WORDS = List.of(
            "add", "improve", "enhance", "clean", "refactor", "optimize",
            "feature", "support", "update", "upgrade", "better", "great"
    );

    public void analyzeCommit(Commit commit) {
        String message = commit.getMessage().toLowerCase();

        long negativeCount = NEGATIVE_WORDS.stream()
                .filter(message::contains).count();
        long positiveCount = POSITIVE_WORDS.stream()
                .filter(message::contains).count();

        if (negativeCount > positiveCount) {
            commit.setSentimentScore(-1.0);
            commit.setSentimentLabel("NEGATIVE");
        } else if (positiveCount > negativeCount) {
            commit.setSentimentScore(1.0);
            commit.setSentimentLabel("POSITIVE");
        } else {
            commit.setSentimentScore(0.0);
            commit.setSentimentLabel("NEUTRAL");
        }
    }
}