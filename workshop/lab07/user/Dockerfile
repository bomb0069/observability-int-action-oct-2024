
# build stage
FROM openjdk:22-ea-21-jdk-slim AS build

WORKDIR /home/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests

# Copy artifact to final stage
FROM eclipse-temurin:21-jre
COPY agent/opentelemetry-javaagent.jar opentelemetry-javaagent.jar

COPY --from=build /home/app/target/*.jar app.jar

ENTRYPOINT ["java","-javaagent:opentelemetry-javaagent.jar", "-jar","app.jar"]