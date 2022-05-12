FROM openjdk:11 AS build-stage
WORKDIR /build
COPY ./mvnw .
COPY ./.mvn/ .mvn/
COPY ./pom.xml .
COPY ./src/ src/
RUN ./mvnw package
FROM openjdk:11
WORKDIR /app
COPY --from=build-stage /build/target/api-*jar /app/api.jar
EXPOSE 8080
CMD ["java", "-jar", "api.jar"]