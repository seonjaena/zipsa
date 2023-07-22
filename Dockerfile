FROM gradle:7.4.1-jdk11-alpine as Builder

WORKDIR /build

ARG HOME

COPY *.gradle gradle.* gradlew ./
COPY gradle ./gradle
COPY src/main ./src/main

RUN --mount=type=cache,target=${HOME}/.gradle ./gradlew clean build -x test && \
    mv build/libs/zipsa-*.jar zipsa.jar

#FROM eclipse-temurin:11.0.18_10-jre-alpine
FROM amazoncorretto:11-alpine3.18

ENV TZ=Asia/Seoul

WORKDIR /usr/local/zipsa

COPY --from=Builder /build/zipsa.jar zipsa.jar
COPY ecs-health-check.sh ./

RUN apk --no-cache add curl && \
    apk --no-cache add tzdata && \
    echo $TZ > /etc/timezone && \
    apk del tzdata

ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "zipsa.jar"]
