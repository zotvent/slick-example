# slick-example

The goal of the project is to show how you can use `slick` library as *Functional Relational Mapping for Scala*.

## Features

- automatically creates table named `books` in the database
- CRUD operations
- REST interface build on Akka HTTP
- *HikaryCP* as connection pool

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

This example uses `postgresql`. Firstly make sure a have one installed on your machine.


After installing create database named `example`. Replace `user` and `password` values in `application.conf` file on your own.

### Dependencies

Download all dependencies before running the project. You can make it from the terminal:

```shell
sbt clean compile
```

### Running

```shell
sbt run
```

If `application.conf` file can't be found automatically by `sbt`. Provide it explicitly:

```shell
sbt run -Dconfig.resource=application.conf
```

### CRUD operations example

You can play with slick through REST interface.

> add book

```shell
curl -X POST \
  http://localhost:8080/books \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: b2650af6-4348-4681-9ad6-6f760c2124b3' \
  -H 'cache-control: no-cache' \
  -d '{
	"id": 1,
	"name": "test name",
	"author": "test author"
}'
```

> get book

```shell
curl -X GET \
  http://localhost:8080/books/1 \
  -H 'Postman-Token: cfea87e8-9f4a-4ab3-914c-3926f0cf5fad' \
  -H 'cache-control: no-cache'
```

> update book

```shell
curl -X PUT \
  http://localhost:8080/books \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 952b1e75-907f-4ff8-a8a3-4b16bb2f6ecd' \
  -H 'cache-control: no-cache' \
  -d '{
	"id": 1,
	"name": "test update name",
	"author": "test update author"
}'
```

> delete book

```shell
curl -X DELETE \
  http://localhost:8080/books/1 \
  -H 'Postman-Token: 826f5900-865e-46fc-bb02-ab4023863d3e' \
  -H 'cache-control: no-cache'
```
