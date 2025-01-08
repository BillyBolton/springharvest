--liquibase formatted sql
--changeset BillyBolton:insert-authors
INSERT INTO authors
    (id,
     name,
     pet_id,
     date_created,
     date_updated,
     created_by,
     updated_by)
values
    ('00000000-0000-0000-0000-000000000001',
     'Dr. Seuss',
     '00000000-0000-0000-0000-000000000001',
     CURRENT_TIMESTAMP,
     CURRENT_TIMESTAMP,
     '99999999-9999-9999-9999-999999999999',
     '99999999-9999-9999-9999-999999999999');
INSERT INTO authors
    (id,
     name,
     pet_id,
     date_created,
     date_updated,
     created_by,
     updated_by)
values
    ('00000000-0000-0000-0000-000000000002',
     'Birago Diop',
     '00000000-0000-0000-0000-000000000002',
     CURRENT_TIMESTAMP,
     CURRENT_TIMESTAMP,
     '99999999-9999-9999-9999-999999999999',
     '99999999-9999-9999-9999-999999999999');