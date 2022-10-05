FROM openjdk:11

ARG JAR_FILE=*.jar
ARG PROFILE
ENV PROFILE $PROFILE

COPY ${JAR_FILE} app.jar

RUN mkdir -p /root/zipsa-files

ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "/app.jar"]