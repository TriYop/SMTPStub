
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/5339bb4587354eafb9961953f6423210)](https://www.codacy.com/app/TriYop/SMTPStub?utm_source=github.com&utm_medium=referral&utm_content=TriYop/SMTPStub&utm_campaign=badger)

This is a stub for SMTP service. 
It aims to answer SMTP requests and store all received emails into local filesystem.
I made it modular in order to be able to change storage system as required (currently, only file based storage is implemented).

I expect in future releases to add a few features like
- TLS support
- a webmail like client based on the emails storage
- some POP3 or IMAP features 

