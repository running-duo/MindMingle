# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

MindMingle is a Spring Boot 2.7.0 application built with Java 17. It appears to be a WeChat Mini Program backend service with automated sign-in scheduling capabilities and Bark push notifications.

## Build and Development Commands

### Build
```bash
mvn clean package
```

### Run Application
```bash
mvn spring-boot:run
```

### Run Tests
```bash
mvn test
```

### Run Single Test
```bash
mvn test -Dtest=ClassName#methodName
```

### Generate API Documentation
API documentation is automatically generated during compilation using smart-doc:
```bash
mvn compile
```
Documentation output: `target/classes/static/doc/index.html`

## Architecture

### Layer Structure

The application follows a standard Spring Boot layered architecture:

- **Controller** (`controller/`) - REST API endpoints
  - `AuthController` - Authentication endpoints
  - `WxAuthController` - WeChat Mini Program verification
  - `WxMessageController` - WeChat message handling

- **Service** (`service/`) - Business logic layer
  - Interface definitions in `service/`
  - Implementations in `service/impl/`
  - `UserDetailsServiceImpl` in `service/auth/` for Spring Security integration

- **DAO** (`dao/`) - Data access layer
  - Extends MyBatis-Plus `ServiceImpl<Mapper, DO>`
  - Provides additional database operations beyond mapper interfaces

- **Mapper** (`mapper/`) - MyBatis-Plus mapper interfaces
  - Minimal interfaces that extend `BaseMapper<DO>`
  - MyBatis-Plus auto-generates common CRUD operations

- **Entity** (`entity/`)
  - `dos/` - Database objects (suffixed with `DO`)
  - `dto/` - Data transfer objects for API requests
  - `vo/` - View objects for API responses

### Key Components

**Security Architecture** (`security/`)
- JWT-based authentication using custom token service
- `SecurityConfiguration` - Spring Security setup with stateless sessions
- `TokenAuthenticationFilter` - Custom filter for JWT token validation
- `TokenService` - Token generation and validation
- `SysAuthenticationProvider` - Custom authentication provider with BCrypt
- Token configuration in `application.yml` under `spring.application.token`

**Scheduled Tasks** (`schedule/`)
- `SignSchedule` - Automated sign-in tasks with Bark push notifications
  - `cpSign()` - Runs daily at 7:00 AM with random delay (0-2 hours)
  - Uses RestTemplate for external API calls
  - Sends results via Bark push notification service

**Configuration**
- `application.yml` - Main configuration with encrypted values (Jasypt)
- Database: MySQL with Druid connection pool
- Cache: Redis for session and data caching
- Database migrations managed by Liquibase (`sql/changelog.yml`)

### Database Management

- **Liquibase** manages schema changes
  - Change log: `src/main/resources/sql/changelog.yml`
  - Initial schema: `src/main/resources/sql/init.sql`
  - Initial data: `src/main/resources/sql/init.data.sql`

- **MyBatis-Plus** provides DAO layer
  - Generator available (`mybatis-plus-generator` dependency)
  - DAOs extend `ServiceImpl` for additional operations
  - Mappers extend `BaseMapper` for basic CRUD

### External Integrations

- **WeChat Mini Program** - OAuth and message handling
  - Config: `miniProgram.appId` and `miniProgram.appSecret` in `application.yml`
  - Verification endpoint: `/wxAuth/verification`

- **Monitoring** - Prometheus metrics enabled
  - Endpoint: `/actuator/prometheus`
  - Configured via Spring Boot Actuator

## Configuration Notes

- Sensitive values in `application.yml` are encrypted with Jasypt (prefix: `ENC(...)`)
- Salt for encryption configured via Jasypt settings
- Token expiration times:
  - Client: 3600s (1 hour)
  - Server: 36000s (10 hours)
  - System: 60s (1 minute)

## API Documentation

The project uses smart-doc for API documentation generation:
- Config: `src/main/resources/smart-doc.json`
- Response codes defined in: `com.aizz.mindmingle.common.ResponseCode`
- Generated docs automatically served at compile time

## Development Server

- Default port: `8236`
- Context path: `/`
- Configured in `application.yml` under `server`
