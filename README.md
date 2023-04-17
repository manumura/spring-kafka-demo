# Demo Spring Boot Kafka

## Building Docker Image

Run spring-boot build image plugin

`mvn spring-boot:build-image -DskipTests`

`docker build -t manumura/spring-kafka-demo .`

`docker run -p 8080:8080 -d --name spring-kafka-demo --env-file .env manumura/spring-kafka-demo`

`docker run -p 8080:8080 -d --name spring-kafka-demo --env-file .env ghcr.io/manumura/spring-kafka-demo:master`

## Run Kafdrop

`cd docker`

`docker compose up`

go to `http://localhost:9000/`
