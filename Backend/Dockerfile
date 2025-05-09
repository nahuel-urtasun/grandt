# Backend/Dockerfile
FROM openjdk:17
COPY . /app
WORKDIR /app
RUN ./gradlew build
CMD ["java", "-jar", "build/libs/tu-app-backend.jar"]
