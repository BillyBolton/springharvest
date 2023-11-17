--liquibase formatted sql
--changeset BillyBolton:create-ca-cities-table-1
-- https://www.canadacitylist.com/specifications/

DROP TABLE IF EXISTS ca_cities CASCADE;
CREATE TABLE ca_cities
(
    id               INTEGER PRIMARY KEY,
    name             VARCHAR(46)      NOT NULL,
    county           VARCHAR(36)      NULL,
    province         VARCHAR(25)      NOT NULL,
    province_code    VARCHAR(2)       NOT NULL,
    postal_code_area VARCHAR(3)       NOT NULL,
    type             VARCHAR(19)      NOT NULL,
    map_reference    VARCHAR(6)       NULL,
    latitude         NUMERIC(8, 5)    NOT NULL,
    longitude        NUMERIC(8, 5)    NOT NULL,
    census_division  VARCHAR(36)      NOT NULL,
    area_code        VARCHAR(3)       NOT NULL,
    time_zone        VARCHAR(21)      NOT NULL,
    point            GEOGRAPHY(POINT) NOT NULL,
    UNIQUE (latitude, longitude),
    UNIQUE (point)
);

