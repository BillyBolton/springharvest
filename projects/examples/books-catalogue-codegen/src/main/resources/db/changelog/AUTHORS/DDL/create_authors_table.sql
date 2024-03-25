--liquibase formatted sql
--changeset BillyBolton:create-authors-table

DROP TABLE IF EXISTS authors CASCADE;
CREATE TABLE authors
(
    id   UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    UNIQUE (name)
) INHERITS (base_trace_parent);