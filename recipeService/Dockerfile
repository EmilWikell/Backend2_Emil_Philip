FROM openjdk:11 AS build-stage
WORKDIR /build
COPY ./mvnw .
COPY ./.mvn/ .mvn/
COPY ./pom.xml .
COPY ./src/ src/
RUN chmod +x mvnw
RUN ./mvnw package
FROM openjdk:11
WORKDIR /appRecipeService
COPY --from=build-stage /build/target/recipeService-*jar /appRecipeService/recipeService.jar
EXPOSE 8085
CMD ["java", "-jar", "recipeService.jar"]