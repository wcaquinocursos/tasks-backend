FROM tomcat:8.5.50-jdk8-openjdk

ARG WAR_FILE
ARG CONTEXT

##Realiza um cópia da origem do tasks.war para imagem do docker |o caminho dentro da imagem docker
COPY ${WAR_FILE} /usr/local/tomcat/webapps/${CONTEXT}.war


## Minha imagem vai partir da tomcat:8.5.50-jdk8-openjdk
## /usr/local/tomcat/webapps --->>> é o local onde é feito os deploys da aplicação .war