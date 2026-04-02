package com.emotionalanalytics.controller;

import com.emotionalanalytics.dto.HealthScoreDTO;
import com.emotionalanalytics.service.HealthScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/healthscore")
@RequiredArgsConstructor
public class HealthScoreController {
    private final HealthScoreService healthScoreService;

    @GetMapping("/{repoId}")
    public HealthScoreDTO getHealthScore (@PathVariable UUID repoId){
        return healthScoreService.calculateHealthScore(repoId);
    }
}
