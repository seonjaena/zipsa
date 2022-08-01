FROM openjdk:11

ARG JAR_FILE=*.jar
ARG PROFILE
ENV PROFILE $PROFILE

RUN apt-get update -y && \
    apt-get install git -y && \
    apt-get install unzip -y && \
    apt-get install zip -y && \
    apt-get install curl -y && \
    apt-get install vim -y

RUN curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip" && \
    unzip awscliv2.zip && \
    ./aws/install

COPY ${JAR_FILE} app.jar


ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILE}", "-jar", "/app.jar"]