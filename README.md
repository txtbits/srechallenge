# SRE Challenge – Content & Product Management

## Overview

A sample project that includes the requirements requested in the SRE position challenge. Includes:
- Simple Spring Boot REST application with a hello world endpoint with the current working environment (dev/pro) and a unit test.
- Jenkins pipeline, with SCM poll.
- Deployment of an application in Kubernetes exposed to the Internet.

## Pre-requisites

* Java JDK 8+
* Gradle (included Gradle Wrapper)
* Jenkins  
* Docker
* Kubernetes

## Questions raised

### How will you ensure the application is deployed properly?
There are multiple ways to check if the application is deployed properly:
- Creating sanity/health check:
    - At application level, enabling Spring Boot Actuator module. 
    - At Jenkins pipeline (e.g curl via Jenkins poll).
- Check docker/kubernetes application running instances.
- Logs at Jenkins, Docker or Kubernetes. Also using `describe` command in Kubernetes.
- Liveness/Readiness probe configured on Kubernetes Deployment.
- With the popular centralized logging solution: Elasticsearch, Fluentd, and Kibana (EFK) stack.

### How can you check the application logs once deployed?
The best way to do it is by using [Kibana](https://www.elastic.co/kibana) from ELK stack. Kibana is a powerful data visualization frontend and dashboard for Elasticsearch. Kibana allows you to explore your Elasticsearch log data through a web interface, and build dashboards and queries to quickly answer questions and gain insight into your Kubernetes applications.

Other possibility in a very simple and at times rudimentary way, is executing command to show logs/describe from Kubernetes resources.
```bash
kubect logs deployment/srechallenge-deployment
```

### Can you be alerted when application is not ready?
As I mentioned in the first question, we could implement a sanity/health check in a jenkins pipeline or at application level.
Also, we could add a liveness/readiness probe in Kubernetes.

With EFK stack we could create alerts too, but a powerful solution could be [Prometheus](https://prometheus.io/), for event monitoring and alerting.

## Manual building

### Testing
`./gradlew test`

### Building (no tests)
`./gradlew assemble`

### Building (with tests)
`./gradlew build`

### Running in Docker
`./gradlew assemble docker dockerRun`

### Stopping Docker container
`./gradlew dockerStop`

## Jenkins pipeline

### Stages

#### Github
Through the Jenkins Github plugin, download the application project from origin repository.
As an example, the repository is public, but it would be best to use some credentials.

Added pollSCM trigger to poll the git repository every 30 minutes for changes to it.

#### Build
Assemble the entire project and obtain an executable jar.

#### Test
Execution of project tests.

#### Build Docker image
Build docker image from DockerFile via docker gradle task.

#### Push Docker image
Push docker image to Docker Hub repository and run a local instance from the generated image.

#### K8s deployment
Deploy in Kubernetes using k8s.yaml config file.

## Using REST API with hello world endpoint

* get helloWorld - GET [/hello](http://localhost:8080/hello) to get a hello world string with the current working environment (dev/pro).

