package com.emotionalanalytics.service;

import com.emotionalanalytics.dto.RepoDTO;
import com.emotionalanalytics.entities.Repo;
import com.emotionalanalytics.repositories.RepoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepoService {

    private final RepoRepository repoRepository;

    private RepoDTO toDTO(Repo repo) {
        return new RepoDTO(
                repo.getId(),
                repo.getName(),
                repo.getGithubUrl(),
                repo.getPlatform(),
                repo.getCreatedAt()
        );
    }
    public RepoDTO addRepo(Repo repo) {
        Repo saved = repoRepository.save(repo);
        return toDTO(saved);
    }

    public List<RepoDTO> getAllRepos(){
        return repoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }
}
