FROM openjdk:8-jre-stretch

ARG JAR_FILE
# cd /opt/app
WORKDIR /opt/app

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]