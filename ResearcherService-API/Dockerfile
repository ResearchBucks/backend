#Use a lightweight OpenJDK image
FROM openjdk:17-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the built jar file
COPY target/ResearcherService-API-0.0.1-SNAPSHOT.jar  app.jar

# Expose port (change if your ServiceRegistry runs on a different port)
EXPOSE 8081

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
