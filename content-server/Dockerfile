FROM openjdk:17

ADD ./target/content-server-0.0.1-SNAPSHOT-exec.jar content-server-0.0.1-SNAPSHOT-exec.jar

EXPOSE 7003

ENTRYPOINT [ "java", "-jar", "content-server-0.0.1-SNAPSHOT-exec.jar" ]
