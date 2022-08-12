FROM openjdk:17

ADD ./target/login-server-0.0.1-SNAPSHOT-exec.jar login-server-0.0.1-SNAPSHOT-exec.jar

EXPOSE 7001

ENTRYPOINT [ "java", "-jar", "login-server-0.0.1-SNAPSHOT-exec.jar" ]
