FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=ext50n123
EXPOSE 3306

RUN mkdir -p /src/database
WORKDIR /src/database

COPY /src/database .
ADD /src/database/all.sql /docker-entrypoint-initdb.d
ADD data.sql /docker-entrypoint-initdb.d
