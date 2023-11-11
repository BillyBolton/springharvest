--liquibase formatted sql
--changeset BillyBolton:create-db-extensions

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
-- CREATE EXTENSION IF NOT EXISTS postgis;