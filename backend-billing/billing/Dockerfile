FROM adoptopenjdk/openjdk11:jdk-11.0.10_9-alpine AS builder
WORKDIR /app
COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar

FROM adoptopenjdk/openjdk11:jdk-11.0.10_9-alpine
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "./build/libs/billing.jar"]
