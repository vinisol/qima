# Technical Assignement Setup with Docker Compose

This project consists of 3 applications that work together:

- **Product Manager** – an Angular frontend app
- **Authentication Service** – a Spring Boot service
- **Product Service** – another Spring Boot service

All three have their own Dockerfiles and can be run together using Docker Compose.

---

## Project Structure

Make sure your folder structure looks like this:

```
qima/
├── docker-compose.yml 
├── auth-service/ 
│   └── Dockerfile 
├── product-service/ 
│   └── Dockerfile
└── product-manager/ 
    └── Dockerfile

```
Each directory contains a `Dockerfile` that knows how to build the respective app.

---

## Architecture Notes

In a real-world microservices setup, introducing an api-gateway-service would improve the structure and security of the system. While not implemented in this assignment for simplicity, an API Gateway would typically sit in front of all services and handle cross-cutting concerns.

### Why Use an API Gateway?

An api-gateway-service acts as a single entry point to the system and can provide:
- Token validation before requests reach downstream services (auth-service, product-service, etc.), reducing duplicated security logic.
- Request forwarding and routing to appropriate services based on the endpoint.
- Centralized logging, metrics, and monitoring, making it easier to track API usage.
- Rate limiting and throttling to protect backend services.
- Service discovery and load balancing for distributed systems.

### Why It Matters Here
In this assignment, having the api-gateway-service would allow token verification to happen once—before the request is passed to auth-service or product-service. This makes the system more secure and scalable while keeping the individual services simpler and more focused.

---

## Prerequisites

Before running this setup, make sure you have:
- [Docker](https://docs.docker.com/get-docker/) installed

---

## How to Run Everything

From the root of the project (where `docker-compose.yml` lives), just run:
```bash
docker-compose up --build
```
This will:
1. Build the Docker images for each app
2. Start all services in one go
3. Attach all containers to a shared Docker network so they can talk to each other

## Access the Apps

Once everything is running:
- Authentication Service: http://localhost:8081
- Product Service: http://localhost:8082
- Product Manager (Angular App): http://localhost:4200

## Default Credentials

You can use the following test users to log in via the Angular frontend (`http://localhost:4200`):

| Username | Password  | Role  | Permissions |
|----------|-----------|-------|-------------|
| admin    | admin     | ADMIN |     all     |
| user     | user      | USER  | list, view  |

## Need to Rebuild?
If you make code changes and want to rebuild everything, stop the containers and run:

```bash
docker-compose down
docker-compose up --build
```
Or, if you're just updating one service:
```bash
docker-compose up --build product-service
```

## How to Stop Everything
To stop the containers, hit Ctrl + C in the terminal, then run:
```bash
docker-compose down
```
This will gracefully shut down all services.