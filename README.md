# SMTP stubbed service. 

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=TriYop_SMTPStub2&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=TriYop_SMTPStub2)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=TriYop_SMTPStub2&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=TriYop_SMTPStub2)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=TriYop_SMTPStub2&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=TriYop_SMTPStub2)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=TriYop_SMTPStub2&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=TriYop_SMTPStub2)
[![Known Vulnerabilities](https://snyk.io/test/github/triyop/smtpstub/badge.svg)](https://snyk.io/test/github/triyop/smtpstub)

It aims to answer SMTP requests and store all received emails into local filesystem.
I made it modular in order to be able to change storage system as required (currently, only file based storage is implemented).

I expect in future releases to add a few features like
- TLS support
- a webmail like client based on the emails storage
- some POP3 or IMAP features in order to embed with mail clients


