FROM openjdk:21-slim
VOLUME /tmp
COPY build/libs/moviediary-0.0.1-SNAPSHOT.jar /app/moviediary-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app/moviediary-0.0.1-SNAPSHOT.jar"]
