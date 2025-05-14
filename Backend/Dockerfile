# Etapa 1: Compilación
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar dependencias primero para aprovechar cache
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el resto del código y compilar
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagen liviana para correr la app
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copiar el jar desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto en el que tu app escucha (por defecto 8080 en Spring Boot)
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
