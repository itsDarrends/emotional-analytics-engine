package com.emotionalanalytics.service;

import com.emotionalanalytics.dto.CommitDTO;
import com.emotionalanalytics.dto.RepoDTO;
import com.emotionalanalytics.entities.Commit;
import com.emotionalanalytics.entities.Repo;
import com.emotionalanalytics.repositories.CommitRepository;
import com.emotionalanalytics.repositories.RepoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommitService{
    private final CommitRepository commitRepository;
    private final RepoRepository repoRepository;

    private CommitDTO toDTO(Commit commit) {
        return new CommitDTO(
                commit.getRepo().getId(),
                commit.getId(),
                commit.getSha(),
                commit.getMessage(),
                commit.getAuthorName(),
                commit.getCommittedAt()
        );
    }
    public List<CommitDTO> getAllCommitsByRepo(UUID repoId){
        return commitRepository.findByRepoId(repoId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
