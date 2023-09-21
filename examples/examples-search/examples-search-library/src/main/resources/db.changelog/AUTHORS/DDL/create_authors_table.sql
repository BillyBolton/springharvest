--liquibase formatted sql
--changeset BillyBolton:create-authors-table

CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";
DROP TABLE IF EXISTS authors CASCADE;
CREATE TABLE authors
(
    id   UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    UNIQUE (name)
);