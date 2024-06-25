# Build stage
FROM gradle:8-jdk21-jammy AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Fügen Sie die ARG- und ENV-Anweisungen hinzu
ARG movieApp_Password
ARG movieApp_Username
ARG MOVIE_DB_API_KEY
ENV MOVIE_DB_API_KEY=${MOVIE_DB_API_KEY}

RUN gradle build --no-daemon

# Runtime stage
FROM openjdk:21-slim
# WORKDIR /app

# Fügen Sie die ENV-Anweisung hinzu, um sicherzustellen, dass die Umgebungsvariable zur Laufzeit verfügbar ist
ENV MOVIE_DB_API_KEY=${MOVIE_DB_API_KEY}

COPY --from=build /home/gradle/src/build/libs/moviediary-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
