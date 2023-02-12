#!/bin/bash

GIT_BRANCH=""
if [ -n "$BRANCH_NAME" ]; then
  GIT_BRANCH=$BRANCH_NAME
else
  GIT_BRANCH=$(git symbolic-ref --short HEAD)
fi

REVISION=$(git rev-parse --short HEAD)
VER=$(cat version.txt | sed 's/ //g')-$GIT_BRANCH
BUILD=$(date +%Y%m%d%H%M)

echo "REVISION=$REVISION"
echo "VER=$VER"
echo "BUILD=$BUILD"
echo "GIT_BRANCH=$GIT_BRANCH"
if [ -n "$BUILD_NUMBER" ]; then
  echo "BUILD_NUMBER(jenkins)=$BUILD_NUMBER"
fi
if [ -n "$BRANCH_NAME" ]; then
  echo "BRANCH_NAME(jenkins)=$BRANCH_NAME"
fi

docker build -t zipsa:$VER --build-arg REVISION=$REVISION --build-arg VER=$VER --build-arg BUILD=$BUILD .