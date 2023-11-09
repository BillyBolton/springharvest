--liquibase formatted sql
--changeset BillyBolton:insert-authors
INSERT INTO books
    (id,
     title,
     author_id,
     publisher_id)
values
    ('00000000-0000-0000-0000-000000000001',
     'The Cat in the Hat',
     '00000000-0000-0000-0000-000000000001',
     '00000000-0000-0000-0000-000000000001');
INSERT INTO books
    (id,
     title,
     author_id,
     publisher_id)
values
    ('00000000-0000-0000-0000-000000000002',
     'The Hobbit',
     '00000000-0000-0000-0000-000000000001',
     '00000000-0000-0000-0000-000000000001');