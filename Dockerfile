# Step 1: Build the application using Maven and Java 17
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run the compiled JAR using a stable, supported Java 17 runtime
FROM eclipse-temurin:17-jre-alpine
# The wildcard target/*.jar captures the file cleanly without version strings
COPY --from=build /target/*.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "demo.jar"]
