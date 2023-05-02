#!bin/bash

export BIN_DIR=$( cd "$(dirname "$0")"; pwd -P )
export HOME_DIR=$( cd "$BIN_DIR"; cd ../../../; pwd -P)
export JAR_FILE=$HOME_DIR/libs/jwt-1.0.0.jar

if [ "$1" != "" ]
then
  export SPRING_PROFILE=$1
else
  export SPRING_PROFILE="dev"
fi

java \
-Dspring.profiles.active=$SPRING_PROFILE \
-jar $JAR_FILE
