--liquibase formatted sql
--changeset BillyBolton:create-publishers-table

DROP TABLE IF EXISTS publishers;
CREATE TABLE publishers(
                                 id SERIAL PRIMARY KEY ,
                                 name VARCHAR(40) NOT NULL,
                                 UNIQUE(name)
);