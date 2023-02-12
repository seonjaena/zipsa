ARG PROFILE

FROM 7.6.0-jdk11-alpine as Builder

WORKDIR /build

COPY build.gradle gradlew ./
COPY src ./src

ARG PROFILE

COPY ${JAR_FILE} app.jar

RUN mkdir -p /root/zipsa-files

FROM openjdk:11.0.11-jre-slim

COPY --from=Builder /build/zipsa /usr/local/zipsa

RUN apk update && \
    apk add --update tzdata

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "/app.jar"]