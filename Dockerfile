FROM openjdk:8-jre-slim
COPY target/file-sharing-1.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
