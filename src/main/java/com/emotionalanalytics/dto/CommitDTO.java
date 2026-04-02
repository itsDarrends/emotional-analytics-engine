package com.emotionalanalytics.dto;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class CommitDTO {

    UUID repoId;
    UUID id;
    String sha;
    String message;
    String authorName;
    LocalDateTime committedAt;
}
