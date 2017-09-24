# SMTP stubbed service. 
[![Build Status](https://www.travis-ci.org/TriYop/SMTPStub.svg?branch=master)](https://www.travis-ci.org/TriYop/SMTPStub)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/5339bb4587354eafb9961953f6423210)](https://www.codacy.com/app/TriYop/SMTPStub?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=TriYop/SMTPStub&amp;utm_campaign=Badge_Grade)

It aims to answer SMTP requests and store all received emails into local filesystem.
I made it modular in order to be able to change storage system as required (currently, only file based storage is implemented).

I expect in future releases to add a few features like
- TLS support
- a webmail like client based on the emails storage
- some POP3 or IMAP features in order to embed with mail clients


