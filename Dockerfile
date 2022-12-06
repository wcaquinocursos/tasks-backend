FROM tomcat:8.5.50-jdk8

ARG WAR_FILE
ARG CONTEXT

COPY ${WAR_FILE} /usr/local/tomcat/webapps/${CONTEXT}.war
#Use command
#$ docker build --build-arg WAR_FILE=tasks-frontend/target/tasks.war --build-arg CONTEXT=tasks  -t IMAGE_NAME
#$ docker build --build-arg WAR_FILE=target/tasks-backend.war --build-arg CONTEXT=tasks-backend  -t IMAGE_NAME
#to build this image.
#Then run
#$ docker run --rm --name tasks-frontend -e BACKEND_HOST=192.168.0.103 -e BACKEND_PORT=8901 -p 8903:8080 tasks-frontend
#to run container




