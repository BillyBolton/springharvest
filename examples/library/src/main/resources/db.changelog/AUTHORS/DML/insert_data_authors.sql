--liquibase formatted sql
--changeset BillyBolton:insert-authors
INSERT INTO authors (id, name)
values (DEFAULT, 'Dr. Seuss');