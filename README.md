# slick-example

The goal of the project is to show how you can use `slick` library as *Functional Relational Mapping for Scala*.

## Features

- docker-compose for automatic database creation
- CRUD operations
- REST interface build on Akka HTTP
- *HikaryCP* as a connection pool
- Unit tests

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing
purposes.

### Database

Run the database (it will automatically create all necessary databases, schemas and tables)

```shell
docker-compose up -d
```

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

- add book

    ```shell
    curl -X POST \
      http://localhost:8080/books \
      -H 'Content-Type: application/json' \
      -d '{
        "id": 1,
        "name": "test name",
        "author": "test author"
    }'
    ```

- get book

    ```shell
    curl -X GET \
      http://localhost:8080/books/1
    ```

- get books

    ```shell
    curl -X GET \
      http://localhost:8080/books
    ```

- update book

    ```shell
    curl -X PUT \
      http://localhost:8080/books \
      -H 'Content-Type: application/json' \
      -d '{
        "id": 1,
        "name": "updated name",
        "author": "updated author"
    }'
    ```

- delete book

    ```shell
    curl -X DELETE \
      http://localhost:8080/books/1
    ```
