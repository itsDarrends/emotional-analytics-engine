package com.emotionalanalytics.repositories;

import com.emotionalanalytics.entities.Commit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommitRepository extends JpaRepository<Commit, UUID> {
    boolean existsBySha(String sha);
    List<Commit> findByRepoId(UUID repoId);
}
