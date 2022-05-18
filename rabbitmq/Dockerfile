FROM openjdk:11 AS build-stage
WORKDIR /build
COPY ./mvnw .
COPY ./.mvn/ .mvn/
COPY ./pom.xml .
COPY ./src/ src/
RUN ./mvnw package
FROM openjdk:11
WORKDIR /appRabbitMQ
COPY --from=build-stage /build/target/rabbitmq-*jar /appRabbitMQ/rabbitmq.jar
EXPOSE 8085
CMD ["java", "-jar", "rabbitmq.jar"]