# Use a lightweight OpenJDK image as the base
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot jar file into the container
COPY target/AdminService-API-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on (optional, for documentation)
EXPOSE 8091

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]