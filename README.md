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

- master-data-service
- user-service

## Local Development

Copy the example environment file:

```bash
cp .env.example .env
```

Update `.env` with your local PostgreSQL username, password, service ports, and database URLs.

Create the local PostgreSQL databases before starting the services:

```sql
CREATE DATABASE mrp_master_data_service;
CREATE DATABASE mrp_user_service;
```

Run each service with the `local` Spring profile enabled.
