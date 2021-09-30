FROM tomcat:8.5.50-jdk11-openjdk

ARG WAR_FILE
ARG CONTEXT

COPY  ${WAR_FILE} /usr/local/tomcat/webapps/${CONTEXT}.war