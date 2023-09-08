--liquibase formatted sql
--changeset BillyBolton:create-authors-table

DROP TABLE IF EXISTS authors;
CREATE TABLE authors(
                           id SERIAL PRIMARY KEY ,
                           name VARCHAR(40) NOT NULL,
                           UNIQUE(name)
);