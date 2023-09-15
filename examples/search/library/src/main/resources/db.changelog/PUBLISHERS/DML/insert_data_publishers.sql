--liquibase formatted sql
--changeset BillyBolton:insert-publishers
INSERT INTO publishers (id, name)
values ('00000000-0000-0000-0000-000000000001', 'Random House');