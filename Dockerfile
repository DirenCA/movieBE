FROM gradle:8-jdk21-jammy AS build
WORKDIR /
COPY . ./
ARG movieApp_Password
ARG movieApp_Username
ARG MOVIE_DB_API_KEY
ENV MOVIE_DB_API_KEY=${MOVIE_DB_API_KEY}
RUN gradle build --no-deamon

FROM openjdk:21-slim
COPY --from=build /home/gradle/src/build/libs/moviediary-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
