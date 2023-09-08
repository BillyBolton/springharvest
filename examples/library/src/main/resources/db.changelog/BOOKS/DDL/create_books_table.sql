--liquibase formatted sql
--changeset BillyBolton:create-books-table

DROP TABLE IF EXISTS books;
CREATE TABLE books(
                        id SERIAL PRIMARY KEY ,
                        title VARCHAR(40) NOT NULL,
                        author_id SERIAL NOT NULL,
                        publisher_id SERIAL NOT NULL,
                        FOREIGN KEY (author_id) REFERENCES AUTHORS(id) ON DELETE CASCADE,
                        FOREIGN KEY (publisher_id) REFERENCES PUBLISHERS(id) ON DELETE CASCADE,
                        UNIQUE(title, author_id)
);