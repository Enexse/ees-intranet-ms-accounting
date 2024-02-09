ARG JAVA_VERSION=19
FROM openjdk:${JAVA_VERSION}
COPY target/ees-intranet-ms-accounting.jar ees-intranet-ms-accounting.jar
EXPOSE 9191
ENTRYPOINT ["java","-jar","/ees-intranet-ms-accounting.jar"]