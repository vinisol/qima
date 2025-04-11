# Product Service

A simple Spring Boot microservice for managing products and categories. It supports filtering, pagination, sorting, and role-based access control via JWT.

This service was created for a technical assignment and is **not production-ready**. It's meant to demonstrate basic functionality and structure.

---

## Getting Started

This section explains how to get the service up and running locally.

### Prerequisites

- Java 21+
- Maven
- Docker (optional)

### Running Locally

#### Option 1: Terminal
```bash
cd product-service
./mvnw spring-boot:run
```

#### Option 2: Docker
Build and run the service using Docker:
```bash
docker build -t product-service .
docker run -p 8082:8082 product-service
```

### Application Structure
Here’s a high-level view of the key components:
- controller: REST endpoints for products and categories
- service: Business logic for CRUD operations and filters
- domain: Domain entities
- dto: Request/Response DTOs
- security: JWT-based access control
- repository: Spring Data JPA for DB access
- config: Security, CORS, and other configurations

### Important Notes
This service is not designed for production. Several essential features and best practices are currently missing.

#### What’s Missing
- Input validation
- Proper error handling
- Secure JWT secret management
- Structured logging and observability
- Persistent database and schema migrations
- Fine-grained CORS config
- Full-featured product filtering

#### Suggestions for Production Readiness

- Add validation with @Valid, @NotBlank, etc.
- Implement @ControllerAdvice for centralized error handling
- Secure JWT secrets via environment variables or secret managers
- Use structured logging (SLF4J) and add tracing/metrics tools
- Switch from H2 to PostgreSQL or MySQL and use Flyway for DB migrations
- Make CORS configurable via properties
- Add improved role-based access
- Add Swagger/OpenAPI for API documentation
- Add unit and integration tests

### API Overview

#### Authentication
Use the header:
`Authorization: Bearer <token>`

Roles:
- ADMIN
- USER

#### Products

| Method | Endpoint.      | Description.               | Roles       |
|--------|----------------|----------------------------|-------------|
| GET	 | /products      | List products (sort).      | USER, ADMIN |
| GET	 | /products/{id} | Get product by ID.         | USER, ADMIN |
| POST	 | /products      | Create a new product.      | ADMIN       |
| PUT	 | /products/{id} | Update an existing product | ADMIN       |
| DELETE | /products/{id} | Delete a product.          | ADMIN       |

##### Query Parameters (GET /products):
- name: Filter by name (case-insensitive) **TO DO**
- available: Filter by availability (true / false) **TO DO**
- page: Page number (default: 0)
- size: Page size (default: 10)
- sort: Field and direction (e.g., name,asc)

#### Categories

| Method | Endpoint.      | Description.               | Roles       |
|--------|----------------|----------------------------|-------------|
| GET	 | /categories    | List all categories        | USER, ADMIN |
