FROM tomcat:9-jdk8-openjdk-slim
ADD target/spring-petclinic-2.0.0.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
