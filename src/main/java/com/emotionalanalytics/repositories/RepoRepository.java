package com.emotionalanalytics.repositories;

import com.emotionalanalytics.entities.Repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RepoRepository extends JpaRepository<Repo, UUID> {
    List<Repo> findByUserId(String userId);
}
