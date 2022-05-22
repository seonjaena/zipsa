@echo off

set PROFILE=dev

IF [%1]==[] (
    echo "PLEASE WRITE PROFILE ID"
    exit 1
)ELSE (
    echo [ PROFILE ID = %1]
    SET PROFILE=%1
)

docker build -t seonjaena/jwt-test .
docker run -p 8080:8080 -d --restart always -e USE_PROFILE=%PROFILE% seonjaena/jwt-test