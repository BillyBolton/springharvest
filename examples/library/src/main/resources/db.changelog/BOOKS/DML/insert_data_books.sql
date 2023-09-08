--liquibase formatted sql
--changeset BillyBolton:insert-authors
INSERT INTO books (id, title, author_id, publisher_id)
values (DEFAULT, 'The Cat in the Hat', 1, 1);