FROM openjdk:17-jdk

WORKDIR /app

ARG JAR_FILE
COPY ${JAR_FILE} application.jar

ENTRYPOINT ["java", "-jar", "/app/application.jar"]
