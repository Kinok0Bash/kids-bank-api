FROM openjdk:21-slim
EXPOSE 3567
COPY ./build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
