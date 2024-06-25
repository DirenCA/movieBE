FROM gradle:8-jdk21-jammy AS build
WORKDIR /
COPY . ./
RUN gradle build

FROM openjdk:21-slim
COPY --from=build /home/gradle/src/build/libs/moviediary-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
