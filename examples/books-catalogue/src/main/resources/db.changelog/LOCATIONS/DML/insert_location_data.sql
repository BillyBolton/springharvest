--liquibase formatted sql
--changeset BillyBolton:insert-location-data
INSERT INTO location
    (id,
     coordinates,
     date_created,
     date_updated,
     created_by,
     updated_by)
values
    ('00000000-0000-0000-0000-000000000001',
     ST_MakePoint(-179.0, -179.0),
     CURRENT_TIMESTAMP,
     CURRENT_TIMESTAMP,
     '99999999-9999-9999-9999-999999999999',
     '99999999-9999-9999-9999-999999999999');
INSERT INTO location
    (id,
     coordinates,
     date_created,
     date_updated,
     created_by,
     updated_by)
values
    ('00000000-0000-0000-0000-000000000002',
     ST_MakePoint(179.0, 179.0),
     CURRENT_TIMESTAMP,
     CURRENT_TIMESTAMP,
     '99999999-9999-9999-9999-999999999999',
     '99999999-9999-9999-9999-999999999999');