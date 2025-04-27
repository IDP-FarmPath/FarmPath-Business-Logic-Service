# build stage
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app

# bring in your POM, plus the local JARs that Maven needs
COPY pom.xml .
COPY lib ./lib

# now copy source and package
COPY src ./src
RUN mvn clean package -DskipTests

# run stage
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
