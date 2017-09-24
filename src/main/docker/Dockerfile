FROM java:8
MAINTAINER TriYop "dev@yvanjanet.net"

RUN mkdir -p /output

COPY SMTPStub-1.0-SNAPSHOT.jar /SMTPStub.jar

VOLUME /output

EXPOSE 25

ENTRYPOINT ["java","-jar","/SMTPStub.jar"]
