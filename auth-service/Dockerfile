FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests
EXPOSE 8081
CMD ["java", "-jar", "target/auth-service-0.0.1-SNAPSHOT.jar"]
