FROM openjdk:21-slim
VOLUME /tmp
COPY build/libs/moviediary-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
