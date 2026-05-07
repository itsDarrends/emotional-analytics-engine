package com.emotionalanalytics.controller;

import com.emotionalanalytics.dto.RepoDTO;
import com.emotionalanalytics.entities.Repo;
import com.emotionalanalytics.service.RepoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repos")
@RequiredArgsConstructor
public class RepoController {

    private final RepoService repoService;

    @PostMapping
    public RepoDTO addRepo(@Valid @RequestBody Repo repo, @AuthenticationPrincipal Jwt jwt) {
        return repoService.addRepo(repo, jwt.getSubject());
    }

    @GetMapping
    public List<RepoDTO> getAllRepos(@AuthenticationPrincipal Jwt jwt) {
        return repoService.getAllRepos(jwt.getSubject());
    }
}
