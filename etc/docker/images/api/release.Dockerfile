FROM gradle:4.10.2-alpine

USER root

COPY . /app

WORKDIR /app

RUN gradle bootJar --parallel --console=verbose

ENTRYPOINT ["java", "-jar", "/app/build/libs/airline-api-0.0.1.jar"]
