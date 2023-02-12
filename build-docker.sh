#!/bin/bash

GIT_BRANCH=""
if [ -n "$BRANCH_NAME" ]; then
  GIT_BRANCH=$BRANCH_NAME
else
  GIT_BRANCH=$(git symbolic-ref --short HEAD)
fi

PROFILE=""
if [ -n "$1" ]; then
  PROFILE=$1
else
  echo "PROFILE을 추가하세요."
  exit 1
fi

VER=$(cat version.txt | sed 's/ //g')
TAG=$VER-$GIT_BRANCH

docker build -t zipsa:$TAG --build-arg PROFILE=$PROFILE --build-arg VER=$VER .