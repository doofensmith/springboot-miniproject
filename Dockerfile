#import base JDK from Linux
FROM adoptopenjdk/openjdk11:alpine

# copy application file
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# expose port
EXPOSE 8080

# run application
ENTRYPOINT ["java","-jar","/app.jar"]