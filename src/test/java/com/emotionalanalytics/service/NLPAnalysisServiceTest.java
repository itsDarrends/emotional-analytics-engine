package com.emotionalanalytics.service;

import com.emotionalanalytics.entities.Commit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class NLPAnalysisServiceTest {

    @InjectMocks
    private NLPAnalysisService nlpAnalysisService;

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

    @Test
    void shouldLabelRevertAsNegative() {
        Commit commit = new Commit();
        commit.setMessage("revert broken authentication change");

        nlpAnalysisService.analyzeCommit(commit);

        assertEquals("NEGATIVE", commit.getSentimentLabel());
    }

    @Test
    void shouldLabelReleaseAsPositive() {
        Commit commit = new Commit();
        commit.setMessage("release v2.0 with new dashboard");

        nlpAnalysisService.analyzeCommit(commit);

        assertEquals("POSITIVE", commit.getSentimentLabel());
    }

    @Test
    void shouldOnlyAnalyzeSubjectLine() {
        Commit commit = new Commit();
        // Subject is positive, body has lots of negative words
        commit.setMessage("add new feature\n\nfix bug\nrevert hack\nerror crash broken");

        nlpAnalysisService.analyzeCommit(commit);

        assertEquals("POSITIVE", commit.getSentimentLabel());
    }
}
