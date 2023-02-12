#!/bin/bash

PROFILE=dev
VER=$(cat version.txt | sed 's/ //g')

export $(cat env/dev/.env | xargs)

./gradlew clean build -x test -Pprofile=$PROFILE
java -Dspring.profiles.active=$PROFILE -jar build/libs/zipsa-$VER.jar