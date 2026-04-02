package com.emotionalanalytics.service;

import com.emotionalanalytics.entities.Commit;
import com.emotionalanalytics.entities.Repo;
import com.emotionalanalytics.repositories.CommitRepository;
import com.emotionalanalytics.repositories.RepoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.emotionalanalytics.service.NLPAnalysisService;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GitHubIngestionService {

    private final RepoRepository repoRepository;
    private final CommitRepository commitRepository;
    private final RestTemplate restTemplate;
    private final NLPAnalysisService nlpAnalysisService;

    public int ingestCommits(UUID repoId, String owner, String repoName) {
        Repo repo = repoRepository.findById(repoId)
                .orElseThrow(() -> new RuntimeException("Repo not found"));

        String url = "https://api.github.com/repos/"
                + owner + "/" + repoName + "/commits";

        List<Map> commits = restTemplate.getForObject(url, List.class);

        int count = 0;

        for (Map item : commits) {
            String sha = (String) item.get("sha");
            Map commitFolder = (Map) item.get("commit");
            String message = (String) commitFolder.get("message");
            Map authorCard = (Map) commitFolder.get("author");
            String authorName = (String) authorCard.get("name");
            String authorEmail = (String) authorCard.get("email");
            String date = (String) authorCard.get("date");
            LocalDateTime committedAt = OffsetDateTime.parse(date).toLocalDateTime();

        if (commitRepository.existsBySha(sha)) continue;
            Commit commit = new Commit();
            commit.setSha(sha);
            commit.setMessage(message);
            commit.setAuthorName(authorName);
            commit.setAuthorEmail(authorEmail);
            commit.setCommittedAt(committedAt);
            commit.setRepo(repo);
            commitRepository.save(commit);
            nlpAnalysisService.analyzeCommit(commit);
            commitRepository.save(commit);

            count++;
        }
        return count;
    }
}

