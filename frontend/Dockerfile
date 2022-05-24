FROM openjdk:11 AS build-stage
WORKDIR /build
COPY ./mvnw .
COPY ./.mvn/ .mvn/
COPY ./pom.xml .
COPY ./src/ src/
RUN chmod +x mvnw
RUN sed -i 's/\r$//' mvnw
RUN ./mvnw package
FROM openjdk:11
WORKDIR /appFrontend
COPY --from=build-stage /build/target/frontend-*jar /appFrontend/frontend.jar
EXPOSE 8080
CMD ["java", "-jar", "frontend.jar"]