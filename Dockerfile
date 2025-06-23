# ---- STAGE 1: Build ----
FROM maven:3.8.5-openjdk-17 AS build

# Set working directory inside the container
WORKDIR /app

# Copy all project files into the container
COPY . .

# Package the application, skipping tests
RUN mvn clean package -DskipTests

# ---- STAGE 2: Runtime ----
FROM openjdk:17.0.1-jdk-slim

# Set working directory
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/JobPortal-0.01-SNAPSHOT.jar JobPortal.jar

# Expose port 8080 to the outside world
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "JobPortal.jar"]
