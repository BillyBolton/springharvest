--liquibase formatted sql
--changeset BillyBolton:create-publishers-table

CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";
DROP TABLE IF EXISTS publishers CASCADE;
CREATE TABLE publishers
(
    id   UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    UNIQUE (name)
) INHERITS (base_trace_parent);