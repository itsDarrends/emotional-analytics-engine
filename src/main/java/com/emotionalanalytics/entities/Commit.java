package com.emotionalanalytics.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "commits")
@Data
public class Commit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String sha;

    @Column(columnDefinition = "TEXT")
    private String message;
    private String authorName;
    private String authorEmail;
    private LocalDateTime committedAt;
    private Double sentimentScore;
    private String sentimentLabel;

    @ManyToOne(fetch = FetchType.LAZY) //many commits belong to one repo
    @JoinColumn(name = "repo_id")
    private Repo repo;
}
