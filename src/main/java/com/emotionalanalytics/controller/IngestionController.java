package com.emotionalanalytics.controller;

import com.emotionalanalytics.service.GitHubIngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/ingest")
@RequiredArgsConstructor
public class IngestionController {
    private final GitHubIngestionService gitHubIngestionService;

    @PostMapping("/commits/{repoId}")
    public Map<String, Object> ingestCommits(
            @PathVariable UUID repoId,
            @RequestParam String owner,
            @RequestParam String repoName) {

    int count = gitHubIngestionService.ingestCommits(repoId, owner, repoName);
        return Map.of("message", "Ingestion complete", "commitsSaved", count);
    }
}
