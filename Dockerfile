FROM maven:3.8.3-openjdk-17 As build
COPY . .
RUN mvn clean package -DSkipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/spring-boot-ecommerce-0.0.1-SNAPSHOT.jar spring-boot-ecommerce.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "spring-boot-ecommerce.jar"]