# Spring Boot Application with Clean Architecture, Docker, Kubernetes, and Helm

This repository implements a Spring Boot application following Clean Architecture principles. 
It includes Dockerization for containerization, Kubernetes configurations for deployment, and a Helm chart for package management.

### Project Structure:
- src/main/java: Core application code following Clean Architecture layers (api, infrastructure, domain)
- src/main/resources: Application configuration files
- Dockerfile: Instructions for building a Docker image of the application
- kubernetes: Kubernetes manifests for deployment (Deployment, Service, etc.)
- helm: Helm chart directory with application package definition

### Prerequisites:
- Java 17+
- Maven (build tool)
- Docker
- Kubernetes cluster

### Clean Architecture:
This project adheres to the Clean Architecture principles, separating concerns and promoting testability.

