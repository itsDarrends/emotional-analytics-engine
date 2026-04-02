# Codebase Emotional Analytics Engine

I built this because I wanted to answer a question nobody talks about:
how does a developer actually feel while writing code?

You can tell a lot from a commit message. "fix this garbage again" is
different from "add user authentication". One is frustrated. One is
productive. Over hundreds of commits, those patterns tell a story about
a team's emotional health that no sprint tracker ever shows you.

## What it does

- Connects to any public GitHub repository
- Pulls down real commit history from the GitHub API
- Runs every commit message through a sentiment engine
- Labels each commit POSITIVE, NEGATIVE, or NEUTRAL
- Calculates a team health score between 0 and 100
- Stores everything in PostgreSQL with full history

## Tech stack

- Java 21 + Spring Boot 3.2
- PostgreSQL + Spring Data JPA
- Spring Security + Clerk JWT authentication
- GitHub REST API for live data ingestion
- Custom sentiment analysis engine

## What I learned building this

The hardest part was connecting all three layers correctly — getting the
controller to talk to the service, the service to the repository, and
none of them crossing boundaries they shouldn't. I also debugged real
Maven dependency errors, Spring package scanning issues, and JWT
authentication from scratch.

## What's next

- Build a frontend dashboard to visualize the health scores over time
- Deploy to a cloud provider
- Add support for PR reviews and comments — not just commits

## API

| Method | Endpoint | What it does |

| POST | `/repos` | Register a repo to track |
| POST | `/ingest/commits/{repoId}?owner=X&repoName=Y` | Ingest commits from GitHub |
| GET  | `/commits/{repoId}` | View analyzed commits |
| GET  | `/healthscore/{repoId}` | Get the team health score |

All endpoints require a Clerk JWT token.

## Setup

1. Clone the repo
2. Create a PostgreSQL database called `emotional_analytics`
3. Copy `application.properties.example` → `application.properties`
4. Add your database credentials and Clerk JWKS URL
5. Run with `mvn spring-boot:run`