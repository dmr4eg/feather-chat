#FROM docker.io/maven:3.9-amazoncorretto-17-debian-bookworm AS build
FROM docker.io/maven:3.8.3-openjdk-17 AS build
WORKDIR /app
ENV HOST=0.0.0.0
ENV PORT=8080
COPY pom.xml .
COPY src src
RUN mvn clean package

FROM docker.io/eclipse-temurin:17-jdk-alpine
WORKDIR /app
ENV HOST=0.0.0.0
ENV PORT=8080
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-jar", "app.jar"]
