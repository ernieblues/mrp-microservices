# MRP Microservices

A microservices-based MRP/business systems platform demonstrating backend API development, relational database design, REST services, Docker/Kubernetes deployment, and React TypeScript frontend integration.

This project is being actively developed as a portfolio application to demonstrate enterprise software development skills across Java Spring Boot, PostgreSQL, Flyway, REST APIs, Docker, Kubernetes, Helm, Task, and modern full-stack architecture.

## Stack

- Java
- Spring Boot
- Gradle (Kotlin DSL)
- PostgreSQL
- React TypeScript
- Docker
- Kubernetes
- Helm
- Task

## Services

- master-data-service
- purchase-requisition-service
- user-service

## Requirements

Before building or deploying the system, ensure the following are installed and configured:

- IntelliJ
- PostgreSQL (for local development)
- Docker Desktop
- Kubernetes enabled in Docker Desktop
- kubectl
- Helm
- Task
- Eclipse Temurin/OpenJDK 21.0.11

Verify the required command-line tools:

| Tool | Install Command | Verify Command |
|---|---|---|
| kubectl | Installed with Docker Desktop or Kubernetes tools | `kubectl get nodes` |
| Helm | `winget install Helm.Helm` | `helm version` |
| Task | `winget install Task.Task` | `task --version` |
| Java 21 | `winget install EclipseAdoptium.Temurin.21.JDK` | `java -version` |

## Local Development

Copy the example environment file:

`cp .env.example .env`

Update the environment values inside `.env`.

```bash
POSTGRES_LOCAL_USER=your_username
POSTGRES_LOCAL_PASSWORD=your_password
```

Create the local PostgreSQL databases before starting the services:

```sql
CREATE DATABASE mrp_master_data_service;
CREATE DATABASE mrp_purchase_requisition_service;
CREATE DATABASE mrp_user_service;
```

Run each service with the `local` Spring profile enabled.

## Kubernetes Deployment

Before deploying to Kubernetes, create the required secret files.

### PostgreSQL Secret

Copy the example secret file:

`cp k8s/postgres-secret.example.yaml k8s/postgres-secret.yaml`

Update the secret values inside `k8s/postgres-secret.yaml`.

```yaml
stringData:
  POSTGRES_USER: your_username
  POSTGRES_PASSWORD: your_password
```

## Task Commands

Run commands from Git Bash at the repository root.

| Command | Description |
|---|---|
| `task` | Build all services and deploy the complete Kubernetes environment. |
| `task build` | Build all Docker images without deploying. |
| `task deploy` | Deploy the complete Kubernetes environment without rebuilding images. |
| `task status` | Check pod status. |
| `task clean` | Remove deployed Kubernetes resources and clean up local port-forward processes. |

## Local Endpoints

After deployment, the following endpoints are available locally:

| Component | Endpoint |
|---|---|
| master-data-service Swagger UI | [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html) |
| purchase-requisition-service Swagger UI | [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html) |
| user-service Swagger UI | [http://localhost:8083/swagger-ui/index.html](http://localhost:8083/swagger-ui/index.html) |
