--liquibase formatted sql
--changeset BillyBolton:create-books-table

DROP TABLE IF EXISTS books CASCADE;
CREATE TABLE books
(
    id           UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    title        VARCHAR(40) NOT NULL,
    author_id    UUID        NOT NULL,
    publisher_id UUID        NOT NULL,
    FOREIGN KEY (author_id) REFERENCES AUTHORS (id) ON DELETE CASCADE,
    FOREIGN KEY (publisher_id) REFERENCES PUBLISHERS (id) ON DELETE CASCADE,
    UNIQUE (title, author_id)
) INHERITS (base_trace_parent);