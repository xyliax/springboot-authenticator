FROM openjdk:17

ADD ./target/eureka-consumer-0.0.1-SNAPSHOT.jar eureka-consumer-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "eureka-consumer-0.0.1-SNAPSHOT.jar" ]
