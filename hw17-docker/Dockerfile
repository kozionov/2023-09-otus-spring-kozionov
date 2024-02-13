FROM openjdk:11-jre-slim
COPY /target/library.jar /app/library.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/library.jar"]
