# Spring Data JPA - Introduction to Spring Data JPA

This repository contains source code examples to support my course Spring Data JPA and Hibernate Beginner to Guru

You can access the API documentation [here](https://sfg-beer-works.github.io/brewery-api/#tag/Beer-Service)

## Connect with Spring Framework Guru
* Spring Framework Guru [Blog](https://springframework.guru/)
* Subscribe to Spring Framework Guru on [YouTube](https://www.youtube.com/channel/UCrXb8NaMPQCQkT8yMP_hSkw)
* Like Spring Framework Guru on [Facebook](https://www.facebook.com/springframeworkguru/)
* Follow Spring Framework Guru on [Twitter](https://twitter.com/spring_guru)
* Connect with John Thompson on [LinkedIn](http://www.linkedin.com/in/springguru)


## MySQL Docker Setup
```shell
docker run --rm \
--name book-db2 \
-e POSTGRES_DB=bookdb2 \
-e POSTGRES_USER=sdjpa \
-e POSTGRES_PASSWORD=PNSJkxXvVNDAhePMuExTBuRR \
-e PGDATA=/var/lib/postgresql/data/pgdata \
-v "$PWD/bookdb2-data:/var/lib/postgresql/data" \
-p 5432:5432 \
postgres:14

```

## Login PostgreSQL
```shell
psql -h 127.0.0.1 -U sdjpa bookdb2
```

## Create User
```sql
CREATE USER bookadmin SUPERUSER CREATEDB CREATEROLE INHERIT LOGIN REPLICATION PASSWORD 'password';
CREATE USER bookuser CREATEDB CREATEROLE INHERIT LOGIN REPLICATION PASSWORD 'password';
```

## Run with profile and skip test
```shell
mvn clean install spring-boot:run -Dspring-boot.run.profiles=local -DskipTests
```