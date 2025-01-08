--liquibase formatted sql
--changeset BillyBolton:insert-authors
INSERT INTO books
    (id,
     title,
     genre,
     author_id,
     publisher_id)
values
    ('00000000-0000-0000-0000-000000000001',
     'The Cat in the Hat',
     'Children''s Literature',
     '00000000-0000-0000-0000-000000000001',
     '00000000-0000-0000-0000-000000000001');
INSERT INTO books
    (id,
     title,
     genre,
     author_id,
     publisher_id)
values
    ('00000000-0000-0000-0000-000000000002',
     'Green Eggs and Ham',
     'Children''s Literature',
     '00000000-0000-0000-0000-000000000001',
     '00000000-0000-0000-0000-000000000001');
INSERT INTO books
    (id,
     title,
     genre,
     author_id,
     publisher_id)
values
    ('00000000-0000-0000-0000-000000000003',
     'How the Grinch Stole Christmas',
     'Children''s Literature',
     '00000000-0000-0000-0000-000000000001',
     '00000000-0000-0000-0000-000000000001');
INSERT INTO books
    (id,
     title,
     genre,
     author_id,
     publisher_id)
values
    ('00000000-0000-0000-0000-000000000004',
     'The Lorax',
     'Children''s Literature',
     '00000000-0000-0000-0000-000000000001',
     '00000000-0000-0000-0000-000000000001');
INSERT INTO books
    (id,
     title,
     genre,
     author_id,
     publisher_id)
values
    ('00000000-0000-0000-0000-000000000005',
     'The Tales Of Amadou Koumba',
     'Folklore',
     '00000000-0000-0000-0000-000000000002',
     '00000000-0000-0000-0000-000000000001');