# MRP Microservices

Material Requirements Planning (MRP) system built with Spring Boot microservices and PostgreSQL.

## Stack

- Java
- Spring Boot
- Gradle (Kotlin DSL)
- PostgreSQL
- React TypeScript
- Docker
- Kubernetes

## Services

- user-service

## Local Development

Copy the example environment file:

```bash
cp .env.example .env
```

Update `.env` with your local PostgreSQL username, password, service port, and database URL.

Create the local PostgreSQL database before starting the service:

```sql
CREATE DATABASE mrp_user_service;
```

Run the service with the `local` Spring profile enabled.
