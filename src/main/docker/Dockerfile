FROM java:8

RUN mkdir -p /output

ADD SMTPStub-1.0-SNAPSHOT.jar /SMTPStub.jar

VOLUME /output

EXPOSE 25

ENTRYPOINT ["java","-jar","/SMTPStub.jar"]
