FROM eclipse-temurin:17
LABEL author=joaquinbotta
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
