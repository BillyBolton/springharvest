--liquibase formatted sql
--changeset BillyBolton:create-base-trace-parent-table

DROP TABLE IF EXISTS base_trace_parent CASCADE;
CREATE TABLE base_trace_parent
(
    created_by UUID,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_by UUID,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);