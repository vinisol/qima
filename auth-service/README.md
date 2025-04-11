# Auth Service

A simple authentication microservice built with Spring Boot. It was originally developed for a technical assignment and is still a work in progress — **not production-ready**.

The service provides a `/auth/login` endpoint that returns a JWT token upon successful authentication.

---

## Getting Started

This section walks you through what you need to run the service locally.

### Prerequisites

- Java 21+
- Maven
- Docker (optional)

### Running Locally

#### Option 1: Terminal
```bash
git clone https://github.com/your-repo/auth-service.git
cd auth-service
./mvnw spring-boot:run
```

#### Option 2: Docker
Build and run the container:
```bash
docker build -t auth-service .
docker run -p 8081:8081 auth-service
```

### Application Structure

Here’s a high-level view of the key components:
- controller: Handles HTTP requests (/auth/login)
- service: Business logic for authentication
- domain: Data classes like User
- dto: Request/Response DTOs
- util: JWT generation
- repository: User data access using Spring Data JPA
- config: Basic app configuration and CORS setup

### Important Notes
This service was designed with simplicity in mind and lacks critical components for real-world use.

#### What’s Missing

- Input validation
- Proper exception handling
- Secure password storage (currently plain text)
- Environment-based config for secrets (currently hardcoded)
- Logging and observability tools
- Persistent database (uses in-memory H2)
- Role-based access control
- Test coverage (integration)
- Token refresh mechanism
- Additional endpoints (registration, user info, etc.)

#### Suggestions for Production Readiness

To move toward production readiness:
- Add request validation using @Valid and annotations like @NotBlank
- Use @ControllerAdvice for centralized exception handling
- Hash passwords with BCrypt
- Store secrets in environment variables or a secrets manager
- Introduce structured logging and avoid sensitive data in logs
- Switch from H2 to PostgreSQL or MySQL and use Flyway for DB migrations
- Implement refresh tokens and logout handling
- Expand API (register, refresh, user info)
- Add integration tests
- Add Swagger/OpenAPI documentation
