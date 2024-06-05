FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/spring-boot-jwt-authentication-0.0.1-SNAPSHOT.jar spring-boot-jwt-authentication.jar
EXPOSE 8095
ENTRYPOINT ["java","-jar","spring-boot-jwt-authentication.jar"]