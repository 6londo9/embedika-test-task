FROM gradle:7.6.1-jdk17

ARG SPRING_PROFILES_ACTIVE

WORKDIR /app

COPY build/libs/embedika-test-task-1.0-SNAPSHOT.jar app.jar

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}"]