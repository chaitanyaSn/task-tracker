FROM eclipse-temurin:21

WORKDIR /app

COPY target/task-app-0.0.1-SNAPSHOT.jar /app/task-app-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "task-app-0.0.1-SNAPSHOT.jar"]
