package com.emotionalanalytics.service;

import com.emotionalanalytics.entities.Commit;
import com.emotionalanalytics.entities.Repo;
import com.emotionalanalytics.repositories.CommitRepository;
import com.emotionalanalytics.repositories.RepoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    @Value("${GITHUB_TOKEN:}")
    private String githubToken;

    public int ingestCommits(UUID repoId, String owner, String repoName) {
        Repo repo = repoRepository.findById(repoId)
                .orElseThrow(() -> new RuntimeException("Repo not found"));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github+json");
        if (githubToken != null && !githubToken.isBlank()) {
            headers.set("Authorization", "Bearer " + githubToken);
        }
        HttpEntity<Void> request = new HttpEntity<>(headers);

        int count = 0;
        int page = 1;

        while (true) {
            String url = "https://api.github.com/repos/" + owner + "/" + repoName
                    + "/commits?per_page=100&page=" + page;

            ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, request, List.class);
            List<Map> commits = response.getBody();

            if (commits == null || commits.isEmpty()) break;

            for (Map item : commits) {
                String sha = (String) item.get("sha");
                if (commitRepository.existsBySha(sha)) continue;

                Map commitFolder = (Map) item.get("commit");
                String message = (String) commitFolder.get("message");
                Map authorCard = (Map) commitFolder.get("author");
                String authorName = (String) authorCard.get("name");
                String authorEmail = (String) authorCard.get("email");
                String date = (String) authorCard.get("date");
                LocalDateTime committedAt = OffsetDateTime.parse(date).toLocalDateTime();

                Commit commit = new Commit();
                commit.setSha(sha);
                commit.setMessage(message);
                commit.setAuthorName(authorName);
                commit.setAuthorEmail(authorEmail);
                commit.setCommittedAt(committedAt);
                commit.setRepo(repo);

                nlpAnalysisService.analyzeCommit(commit);
                commitRepository.save(commit);

                count++;
            }

            if (commits.size() < 100) break;
            page++;
        }

        return count;
    }
}
