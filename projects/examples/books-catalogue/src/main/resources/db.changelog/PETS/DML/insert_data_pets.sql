--liquibase formatted sql
--changeset NeroNemesis:insert-pets
INSERT INTO pets
    (id,
     name)
 values
    ('00000000-0000-0000-0000-000000000001',
     'Greyson');
INSERT INTO pets
    (id,
     name)
values
    ('00000000-0000-0000-0000-000000000002',
     'Rex');