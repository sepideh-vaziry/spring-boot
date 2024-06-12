Spring Boot Clean Architecture Application (Docker, Kubernetes, and Helm)
=================

Welcome to the Spring Boot Clean Architecture application! This project demonstrates a clean and scalable approach to building a Spring Boot application. 
It is dockerized and ready to be deployed on Kubernetes using Helm charts.

Table of contents
=================
<!--ts-->
* [Overview](#Overview)
* [Clean Architecture](#Clean Architecture)
* [Project Structure](#Project Structure)
* [Getting Started](#Getting Started)
* [Building the Project](#Building the Project)
* [Dockerization](#Dockerization)
* [Kubernetes Deployment](#Kubernetes Deployment)
* [Helm Charts](#Helm Charts)
<!--te-->

Overview
=================
This project is a Spring Boot application designed with Clean Architecture principles to ensure maintainability, testability, and scalability. It includes the following features:

* Clean Architecture design
* Docker support
* Kubernetes manifests for deployment
* Helm charts for easier Kubernetes management

Clean Architecture
=================
The Clean Architecture approach separates the code into layers, ensuring a clear dependency rule: 
outer layers can depend on inner layers, but inner layers should not depend on outer layers. 
The primary layers include:
* Domain: Contains the core business logic
* Infrastructure: Persistence & External Access
* API: Controllers

Project Structure
=================
```
.
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── com.example
│   │   │   │   ├── api
│   │   │   │   ├── domain
│   │   │   │   └── infrastructure
│   │   ├── resources
│   └── test
├── Dockerfile
├── helm
│   ├── Chart.yaml
│   ├── values.yaml
│   └── templates
│       ├── configmap.yaml
│       ├── deployment.yaml
│       ├── ingress.yaml
│       ├── prometheusrule.yaml
│       └── service.yaml
├── gitlab-ci.yml
│       └── docker-build.yml
├── .gitlab-ci
└── README.md

```

Getting Started
=================
Prerequisites:
* Java 17+
* Maven (build tool)
* Docker
* Kubernetes cluster
* Helm

Building the Project
=================
To build the project, use the following command:
```shell
mvn clean package
```

Dockerization
=================
To build and run the Docker image:
1. Build the Docker image:
    ```shell
    docker build -t your-image-name:image-tag .
    ```
2. Run the Docker container:
    ```shell
    docker run -p 8080:8080 your-image-name:image-tag
    ```

Kubernetes Deployment
=================
To deploy the application on Kubernetes:
1. Apply the deployment and service manifests:
    ```shell
    kubectl apply -f helm/templates/deployment.yaml
    kubectl apply -f helm/templates/service.yaml
    ```
2. (Optional) Apply the ingress manifest if you have an ingress controller set up:
    ```shell
    kubectl apply -f helm/templates/ingress.yaml
    ```

Helm Charts
=================
To deploy the application using Helm:
1. helm install
    ```shell
    helm install -namespace your-namespace --set image.tag=CI_COMMIT_TAG demo-release helm
    ```
2. To upgrade the release with new changes:
    ```shell
    helm upgrade -namespace your-namespace --set image.tag=CI_COMMIT_TAG demo-release helm
    ```
3. To uninstall the release:
    ```shell
    helm uninstall demo-release
    ```