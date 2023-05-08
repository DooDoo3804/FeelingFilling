FROM adoptopenjdk/openjdk11:jdk-11.0.10_9-alpine AS builder
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar
FROM adoptopenjdk/openjdk11:jdk-11.0.10_9-alpine
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Duser.timezone=Asia/Seoul", "-jar","/app.jar"]

FROM nginx
RUN mkdir /app
WORKDIR /app
RUN rm /etc/nginx/conf.d/default.conf
COPY ./nginx.conf /etc/nginx/conf.d
COPY --from=builder /app/build /app/build
EXPOSE 8080
CMD ["nginx", "-g", "daemon off;"]