ARG JAVA_VERSION=15
FROM openjdk:${JAVA_VERSION}
COPY target/ees-intranet-ms-accounting.jar ees-intranet-ms-accounting.jar
EXPOSE 9191
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","/ees-intranet-ms-accounting.jar"]