--liquibase formatted sql
--changeset BillyBolton:create-authors-table

DROP TABLE IF EXISTS authors CASCADE;
CREATE TABLE authors
(
    id   UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    pet_id UUID,
    FOREIGN KEY (pet_id) REFERENCES PETS (id) ON DELETE SET NULL,
    UNIQUE (pet_id, name)
) INHERITS (base_trace_parent);