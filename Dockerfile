FROM eclipse-temurin:21-jdk
WORKDIR /app
# COPY target/my-app.jar app.jar
COPY . .
RUN chmod +x ./mvnw
EXPOSE 5000
# ENTRYPOINT ["java", "-jar", "app.jar"]
ENTRYPOINT ["./mvnw", "spring-boot:run"]