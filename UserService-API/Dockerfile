# Use a base image with Java runtime
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the jar file (ensure it's built before this step)
COPY target/UserService-API-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on (e.g., 8080)
EXPOSE 8080

# Default command to run your Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]