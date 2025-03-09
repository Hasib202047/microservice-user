FROM amazoncorretto:21
ENV TZ=Asia/Dhaka

ADD user-server.jar user-server.jar
ENTRYPOINT ["java", "-jar", "/user-server.jar"]
