FROM java:8-jre-alpine

RUN mkdir /app
COPY ./build/libs/helidon-1.0-all.jar /app/

EXPOSE 4000

CMD ["java", "-jar", "/app/helidon-1.0-all.jar"]
