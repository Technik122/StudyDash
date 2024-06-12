# Use a Maven image with OpenJDK 17 for the build stage
FROM maven:3.8.4-openjdk-17 AS build

# Copy project files and set the working directory
COPY . /home/maven/src
WORKDIR /home/maven/src

# Build the project and create the JAR file
RUN mvn clean package -DskipTests

# Use a lightweight OpenJDK image for the final stage
FROM openjdk:17-jre-slim

# Create a directory for the application
RUN mkdir /app

# Copy the generated JAR file from the build stage to the /app directory
COPY --from=build /home/maven/src/target/*.jar /app/server-0.0.1-SNAPSHOT.jar

# Expose port 8080
EXPOSE 8080

# Set the startup command for the Docker image
ENTRYPOINT ["java", "-jar", "/app/server-0.0.1-SNAPSHOT.jar"]
