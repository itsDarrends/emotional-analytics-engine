package com.emotionalanalytics.dto;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class RepoDTO {

    UUID id;
    String name;
    String githubUrl;
    String platform;
    LocalDateTime createdAt;
}
