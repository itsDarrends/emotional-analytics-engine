package com.emotionalanalytics.service;

import com.emotionalanalytics.entities.Commit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class NLPAnalysisService {

    // Words that signal problems, frustration, or setbacks in commit history
    private static final List<String> NEGATIVE_WORDS = List.of(
            "fix", "bug", "error", "crash", "broken", "break", "broke",
            "fail", "failure", "failed", "wrong", "issue", "problem",
            "revert", "rollback", "hack", "workaround", "hotfix",
            "regression", "leak", "deadlock", "overflow", "exception",
            "timeout", "flaky", "oops", "typo", "incorrect", "missing",
            "corrupt", "invalid", "slow", "ugly", "nasty", "mess",
            "critical", "urgent", "disable", "todo", "fixme", "hardcoded",
            "conflict", "undo", "stuck", "weird", "pain", "nightmare",
            "emergency", "broken", "panic", "bad", "terrible", "awful"
    );

    // Words that signal progress, improvement, or achievement
    private static final List<String> POSITIVE_WORDS = List.of(
            "add", "implement", "feature", "improve", "enhance", "optimize",
            "clean", "refactor", "release", "support", "upgrade", "create",
            "build", "introduce", "enable", "simplify", "complete", "finish",
            "done", "initial", "setup", "redesign", "restructure", "streamline",
            "migrate", "integrate", "efficient", "new", "ship", "deploy",
            "launch", "resolve", "close", "update", "boost", "speed",
            "better", "great", "smooth", "polish", "nice", "improve",
            "performance", "beautiful", "elegant", "awesome", "solid"
    );

    public void analyzeCommit(Commit commit) {
        String subject = commit.getMessage().split("\n")[0].trim().toLowerCase();

        if (subject.isEmpty()) {
            commit.setSentimentScore(0.0);
            commit.setSentimentLabel("NEUTRAL");
            return;
        }

        long negativeCount = NEGATIVE_WORDS.stream()
                .filter(word -> containsWord(subject, word))
                .count();
        long positiveCount = POSITIVE_WORDS.stream()
                .filter(word -> containsWord(subject, word))
                .count();

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

    private boolean containsWord(String text, String word) {
        return Pattern.compile("\\b" + Pattern.quote(word) + "\\b").matcher(text).find();
    }
}
