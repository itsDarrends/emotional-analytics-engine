spring.application.name=emotional-analytics
server.port=8080

# Database connection
spring.datasource.url=jdbc:postgresql://localhost:5432/emotional_analytics
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false

# Clerk JWKS URL
spring.security.oauth2.resourceserver.jwt.jwk-set-uri=your_clerk_jwks_url