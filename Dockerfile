# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
WORKDIR /app/model
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/model/target/model-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8044
ENTRYPOINT ["java", "-jar", "app.jar"]
