FROM openjdk:17

ADD ./target/auth-server-0.0.1-SNAPSHOT-exec.jar auth-server-0.0.1-SNAPSHOT-exec.jar

EXPOSE 7002

ENTRYPOINT [ "java", "-jar", "auth-server-0.0.1-SNAPSHOT-exec.jar" ]
