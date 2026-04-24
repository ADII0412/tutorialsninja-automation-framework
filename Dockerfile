FROM ubuntu:latest
LABEL authors="adi41"

ENTRYPOINT ["top", "-b"]