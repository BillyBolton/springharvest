--liquibase formatted sql
--changeset BillyBolton:create-ca-cities-distance-table

DROP TABLE IF EXISTS ca_cities_distance;
CREATE TABLE ca_cities_distance
(
    id           UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    source_point GEOGRAPHY(POINT) NOT NULL,
    target_point GEOGRAPHY(POINT) NOT NULL,
    km_distance  DOUBLE PRECISION NOT NULL,
    UNIQUE (source_point, target_point)
);