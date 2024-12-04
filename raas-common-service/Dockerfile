FROM mcr.microsoft.com/mssql/server:2019-CU12-ubuntu-20.04

WORKDIR /etc
COPY create-database.sql /etc
COPY run-initialization.sh /etc
COPY entrypoint.sh /etc

CMD /bin/sh /etc/entrypoint.sh

