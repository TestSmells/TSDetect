FROM amazoncorretto:17.0.4
VOLUME /tmp
COPY build/libs/tsdetect-server.jar tsdetect-server.jar
ENTRYPOINT ["java","-jar","/tsdetect-server.jar"]