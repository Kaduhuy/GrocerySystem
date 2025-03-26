# Use OpenJDK 21
FROM openjdk:21-jdk-slim

# Set working directory in the container
WORKDIR /app

# Copy JAR file into container
COPY target/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
