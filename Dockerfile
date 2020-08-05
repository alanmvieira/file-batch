FROM openjdk:11-jre
RUN mkdir app
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/file-batch.jar
WORKDIR /app
ENTRYPOINT java -jar file-batch.jar