# JobConnect API

**Projet de programmation WEB - Master 1 Miage Dauphine**

Developed by: Omar Soliman

## Overview

JobConnect is a job application system API developed with Spring Boot that provides RESTful endpoints for managing companies, jobs, applicants, applications, skills, and work experiences.

## Features

- Company management
- Job posting and management
- Applicant profiles with skills and work experiences
- Job application handling
- RESTful API with proper relationship handling
- API documentation with Swagger/OpenAPI

## Technology Stack

- Java 21
- Spring Boot 3.4.5
- Spring Data JPA
- PostgreSQL Database
- OpenAPI 3 (Swagger) for documentation

## Database Setup

1. Create a PostgreSQL database named `job_portal`:
   ```sql
   CREATE DATABASE job_portal;
   ```

2. The application will create the necessary tables on startup

## Running the Application

1. Clone the repository
2. Configure PostgreSQL connection in `application.properties` if needed
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
4. The application will start on port 8080

## API Documentation

Once the application is running, you can access the API documentation at:

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/api-docs

## API Endpoints

The API provides the following endpoints:

### Companies
- `GET /api/companies` - Get all companies
- `POST /api/companies` - Create a new company
- `GET /api/companies/{id}` - Get a specific company
- `PUT /api/companies/{id}` - Update a company
- `DELETE /api/companies/{id}` - Delete a company
- `GET /api/companies/{id}/jobs` - Get all jobs for a company

### Jobs
- `GET /api/jobs` - Get all jobs
- `POST /api/jobs` - Create a new job
- `GET /api/jobs/{id}` - Get a specific job
- `PUT /api/jobs/{id}` - Update a job
- `DELETE /api/jobs/{id}` - Delete a job
- `GET /api/jobs/{id}/applications` - Get all applications for a job

### Applicants
- `GET /api/applicants` - Get all applicants
- `POST /api/applicants` - Create a new applicant
- `GET /api/applicants/{id}` - Get a specific applicant
- `PUT /api/applicants/{id}` - Update an applicant
- `DELETE /api/applicants/{id}` - Delete an applicant
- `GET /api/applicants/{id}/applications` - Get all applications for an applicant
- `GET /api/applicants/{id}/experiences` - Get all experiences for an applicant

### Applications
- `GET /api/applications` - Get all applications
- `POST /api/applications` - Create a new application
- `GET /api/applications/{id}` - Get a specific application
- `DELETE /api/applications/{id}` - Delete an application

### Skills
- `GET /api/skills` - Get all skills
- `POST /api/skills` - Create a new skill
- `GET /api/skills/{id}` - Get a specific skill
- `PUT /api/skills/{id}` - Update a skill
- `DELETE /api/skills/{id}` - Delete a skill

### Experiences
- `GET /api/experiences` - Get all experiences
- `POST /api/experiences` - Create a new experience
- `GET /api/experiences/{id}` - Get a specific experience
- `PUT /api/experiences/{id}` - Update an experience
- `DELETE /api/experiences/{id}` - Delete an experience 