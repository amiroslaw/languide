#
# Build stage
#
FROM maven:3.8.2-jdk-11 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM eclipse-temurin:21-jre-alpine
COPY --from=build /target/languide-0.0.1-SNAPSHOT.jar languide.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","languide.jar"]
