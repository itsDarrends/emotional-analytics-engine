package com.emotionalanalytics.service;

import com.emotionalanalytics.entities.Commit;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

@Service
public class NLPAnalysisService {

    private StanfordCoreNLP pipeline;

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    public void analyzeCommit(Commit commit) {
        // Only analyze the subject line — body is noise for sentiment
        String subject = commit.getMessage().split("\n")[0].trim();
        if (subject.isEmpty()) {
            commit.setSentimentScore(0.0);
            commit.setSentimentLabel("NEUTRAL");
            return;
        }

        Annotation annotation = new Annotation(subject);
        pipeline.annotate(annotation);

        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        if (sentences == null || sentences.isEmpty()) {
            commit.setSentimentScore(0.0);
            commit.setSentimentLabel("NEUTRAL");
            return;
        }

        // Average raw score across sentences (0=Very Negative … 4=Very Positive)
        double total = 0;
        for (CoreMap sentence : sentences) {
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            total += RNNCoreAnnotations.getPredictedClass(tree);
        }
        double avg = total / sentences.size();

        if (avg < 1.5) {
            commit.setSentimentScore(-1.0);
            commit.setSentimentLabel("NEGATIVE");
        } else if (avg > 2.5) {
            commit.setSentimentScore(1.0);
            commit.setSentimentLabel("POSITIVE");
        } else {
            commit.setSentimentScore(0.0);
            commit.setSentimentLabel("NEUTRAL");
        }
    }
}
