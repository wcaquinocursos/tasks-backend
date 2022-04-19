FROM tomcat:9.0.62-jdk11-corretto

COPY tomcat-conf/server.xml /usr/local/tomcat/conf/

COPY target/tasks-backend.war webapps

CMD ["catalina.sh", "run"]