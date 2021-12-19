FROM adoptopenjdk:14-jre-hotspot
WORKDIR /opt/app
COPY target/*.jar frontend.jar
CMD ["java", "-jar", "frontend.jar"]