FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the pom.xml and install dependencies
COPY ./pom.xml .
RUN mvn dependency:go-offline

# Copy the source code and build the application
COPY ./src ./src
RUN mvn package -DskipTests

FROM eclipse-temurin:17-jre
# the JAR file path
WORKDIR /app

# Copy the JAR file from the build stage to the final image
COPY --from=build /app/target/*.jar /app/application.jar

# Set the default command to run the Java application
ENTRYPOINT ["java", "-Xmx768M", "-jar", "/app/application.jar"]
