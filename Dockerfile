FROM openjdk:21-slim
VOLUME /tmp
COPY /Users/direnakkaya/WebTechModul/movieBE/build/libs/movieBE-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
