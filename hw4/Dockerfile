# Stage 1: Build
FROM maven:3.8.1-openjdk-11-slim AS build
WORKDIR /workspace/app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# This could be useful for reusing the build cache 
# When an app's source code changes, this RUN command will be re-executed
RUN mvn dependency:go-offline -B

# Copy other source code files
COPY src src

# Build a JAR file
RUN mvn package -DskipTests

# Stage 2: Run 
FROM openjdk:11-jdk
WORKDIR /app

# Install OpenJFX
RUN apt-get update && apt-get install -y openjfx

# Copy the jar file from the build stage
COPY --from=build /workspace/app/target/*.jar app.jar

# Run the application
CMD ["java", "--module-path", "/usr/share/openjfx/lib", "--add-modules", "javafx.controls,javafx.fxml", "-jar", "app.jar"]
