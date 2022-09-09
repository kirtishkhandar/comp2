FROM openjdk:8-jdk-alpine
LABEL maintainer="kirtishkhandar"
ADD target/original-spring-boot-security-jwt-mongodb-0.0.1-SNAPSHOT.jar tweetapp-backend.jar
ENTRYPOINT ["java", "-jar", "tweetapp-backend.jar"]