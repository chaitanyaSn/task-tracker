#FROM eclipse-temurin:21
#
#WORKDIR /app
#
#COPY target/task-app-0.0.1-SNAPSHOT.jar /app/task-app-0.0.1-SNAPSHOT.jar
#
#EXPOSE 8080
#
#ENTRYPOINT ["java", "-jar", "task-app-0.0.1-SNAPSHOT.jar"]


# -------- Build Stage --------
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests package

# -------- Runtime Stage --------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /build/target/task-app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]

