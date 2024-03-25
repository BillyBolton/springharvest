--liquibase formatted sql
--changeset BillyBolton:insert-authors
INSERT INTO authors
    (id,
     name,
     date_created,
     date_updated,
     created_by,
     updated_by)
values
    ('00000000-0000-0000-0000-000000000001',
     'Dr. Seuss',
     CURRENT_TIMESTAMP,
     CURRENT_TIMESTAMP,
     '99999999-9999-9999-9999-999999999999',
     '99999999-9999-9999-9999-999999999999');