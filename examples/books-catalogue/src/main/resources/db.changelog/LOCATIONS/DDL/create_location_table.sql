--liquibase formatted sql
--changeset BillyBolton:create-location-table

CREATE TABLE location
(
    id          UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    coordinates geography(Point, 4326)
) INHERITS (base_trace_parent);