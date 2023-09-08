--liquibase formatted sql
--changeset BillyBolton:insert-publishers
INSERT INTO publishers (id, name)
values (DEFAULT, 'Random House');