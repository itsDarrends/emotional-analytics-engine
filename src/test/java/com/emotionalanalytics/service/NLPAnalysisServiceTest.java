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

    //test 1 (negative)
    @Test
    void shouldLabelNegativeCommit() {
        //Given
        Commit commit = new Commit();
        commit.setMessage("fix this garbage bug");

        //when
        nlpAnalysisService.analyzeCommit(commit);

        //then
        assertEquals("NEGATIVE", commit.getSentimentLabel());
        assertEquals(-1.0, commit.getSentimentScore());
    }

    //test 2 (positive)
    @Test
    void shouldLabelPositiveCommit(){
        //Given
        Commit commit = new Commit();
        commit.setMessage("add new feature to improve performance");

        //when
        nlpAnalysisService.analyzeCommit(commit);

        //then
        assertEquals("POSITIVE", commit.getSentimentLabel());
        assertEquals(1.0, commit.getSentimentScore());
    }

    //test 3 (nutral)
    @Test
    void shouldLabelNeutralCommit(){
        //Given
        Commit commit = new Commit();
        commit.setMessage("merge branch into main");

        //when
        nlpAnalysisService.analyzeCommit(commit);

        //then
        assertEquals("NEUTRAL", commit.getSentimentLabel());
        assertEquals(0.0, commit.getSentimentScore());
    }
}
