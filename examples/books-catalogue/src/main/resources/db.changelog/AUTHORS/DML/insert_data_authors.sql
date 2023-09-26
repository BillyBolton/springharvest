--liquibase formatted sql
--changeset BillyBolton:insert-authors
INSERT INTO authors
    (id,
     name)
values
    ('00000000-0000-0000-0000-000000000001',
     'Dr. Seuss');