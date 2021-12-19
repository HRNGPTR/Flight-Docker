FROM adoptopenjdk:14-jre-hotspot
WORKDIR /opt/app
COPY target/*.jar backend.jar
CMD ["java", "-jar", "backend.jar"]