# Start with a base image that includes Java 21 (use the official OpenJDK image)
FROM openjdk:21-slim

# Set environment variable for the application home directory
ENV APP_HOME=/usr/app/

# Create the application directory
RUN mkdir -p $APP_HOME

# Set the working directory to the application directory
WORKDIR $APP_HOME

# Expose the port your application will run on
EXPOSE 8080

# Copy the executable jar file into the image
# Ensure that the path to the jar file is correct and matches your build output
COPY build/libs/moviediary-0.0.1-SNAPSHOT.jar /app/

# Run the Spring Boot application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]
