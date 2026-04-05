package com.emotionalanalytics.controller;

import com.emotionalanalytics.dto.RepoDTO;
import com.emotionalanalytics.entities.Repo;
import com.emotionalanalytics.service.RepoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/repos")
@RequiredArgsConstructor
public class RepoController {

    private final RepoService repoService;
    @PostMapping
    public RepoDTO addRepo(@Valid @RequestBody Repo repo) {
        return repoService.addRepo(repo);
    }

    @GetMapping
    public List<RepoDTO> getAllRepos() {
        return repoService.getAllRepos();
    }
}