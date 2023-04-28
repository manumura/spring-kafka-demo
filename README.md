# Demo Spring Boot Kafka

## Building Docker Image

Run spring-boot build image plugin

`mvn spring-boot:build-image`

`mvn spring-boot:build-image -Dspring-boot.build-image.imageName=ghcr.io/manumura/spring-kafka-demo:latest`

`mvn spring-boot:build-image -Pnative`

Run Docker build with Dockerfile

`docker build -t manumura/spring-kafka-demo .`

`docker run --rm -p 8080:8080 --name spring-kafka-demo --env-file .env manumura/spring-kafka-demo:0.0.1-SNAPSHOT`

`docker run --rm -p 8080:8080 --name spring-kafka-demo --env-file .env ghcr.io/manumura/spring-kafka-demo:master`

## Run Kafdrop

`cd docker`

`docker compose up`

go to `http://localhost:9000/`
