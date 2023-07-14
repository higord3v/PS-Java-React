#
# package
#
FROM maven:3.9.0-eclipse-temurin-17-alpine AS build

COPY . .

RUN mvn clean package

#
# build
#
FROM openjdk:11

COPY --from=build ./target/banco-0.0.1-SNAPSHOT.jar banco-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "banco-0.0.1-SNAPSHOT.jar"]