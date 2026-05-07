package com.emotionalanalytics.service;

import com.emotionalanalytics.entities.Commit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NLPAnalysisServiceTest {

    private final NLPAnalysisService nlpAnalysisService = new NLPAnalysisService();

    @BeforeAll
    void setUp() {
        // Loading CoreNLP models is slow — do it once for the whole test class
        nlpAnalysisService.init();
    }

    @Test
    void shouldLabelNegativeCommit() {
        Commit commit = new Commit();
        commit.setMessage("fix this garbage bug");

        nlpAnalysisService.analyzeCommit(commit);

        assertEquals("NEGATIVE", commit.getSentimentLabel());
        assertEquals(-1.0, commit.getSentimentScore());
    }

    @Test
    void shouldLabelPositiveCommit() {
        Commit commit = new Commit();
        commit.setMessage("add new feature to improve performance");

        nlpAnalysisService.analyzeCommit(commit);

        assertEquals("POSITIVE", commit.getSentimentLabel());
        assertEquals(1.0, commit.getSentimentScore());
    }

    @Test
    void shouldLabelNeutralCommit() {
        Commit commit = new Commit();
        commit.setMessage("merge branch into main");

        nlpAnalysisService.analyzeCommit(commit);

        assertEquals("NEUTRAL", commit.getSentimentLabel());
        assertEquals(0.0, commit.getSentimentScore());
    }
}
