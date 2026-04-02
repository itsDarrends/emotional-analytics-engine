package com.emotionalanalytics.controller;

import com.emotionalanalytics.dto.CommitDTO;
import com.emotionalanalytics.service.CommitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/commits")
@RequiredArgsConstructor
public class CommitController {
    private final CommitService commitService;

    @GetMapping("/{repoId}")
    public List<CommitDTO> getAllCommitsByRepo(@PathVariable UUID repoId) {
        return commitService.getAllCommitsByRepo(repoId);
    }
}
