FROM gradle:4.10.2

USER root

COPY . /app

WORKDIR /app

RUN gradle bootJar
