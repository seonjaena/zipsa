#!/bin/bash

if [[ ! -z "$1" ]]
then
    export PROFILE=$1
else
    echo "PLEASE WRITE PROFILE ID"
    exit 1
fi

docker build -t seonjaena/jwt-test .
docker run -p 8080:8080 -d --restart always -e USE_PROFILE=$PROFILE seonjaena/jwt-test