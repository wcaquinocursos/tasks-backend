FROM openjdk:17-alpine
ARG JARFILE
WORKDIR /opt
COPY ./target/${JARFILE} /opt/app.jar
CMD ["java", "-jar", "/opt/app.jar"]
