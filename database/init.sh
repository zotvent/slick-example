#!/usr/bin/env bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE slick_example_test;
    CREATE SCHEMA book_store;

    CREATE TABLE IF NOT EXISTS book_store.books
    (
      id INTEGER NOT NULL CONSTRAINT books_pkey PRIMARY KEY,
      name VARCHAR NOT NULL,
      author VARCHAR NOT NULL
    );
EOSQL