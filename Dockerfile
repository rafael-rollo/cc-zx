FROM openjdk:8-jdk-alpine
MAINTAINER Rafael Rollo <rafaelrollo92@gmail.com>
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} webapp.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/webapp.jar"]