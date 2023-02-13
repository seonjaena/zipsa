ARG PROFILE
ARG VER
ARG HOME

FROM gradle:7.6.0-jdk11-alpine as Builder

WORKDIR /build

COPY build.gradle gradlew ./
COPY src/main ./src/main

ARG PROFILE
ARG VER

RUN --mount=type=cache,target=${HOME}/.gradle ./gradlew clean build -x test -Pprofile=$PROFILE

FROM eclipse-temurin:11.0.18_10-jre-alpine

ARG PROFILE
ENV USE_PROFILE ${PROFILE}
ARG VER

WORKDIR /usr/local/zipsa

COPY --from=Builder /build/build/libs/build-${VER}.jar app.jar

RUN apk update && \
    apk add --update tzdata

ENTRYPOINT ["java", "-Dspring.profiles.active=${USE_PROFILE}", "-jar", "app.jar"]