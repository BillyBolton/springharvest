--liquibase formatted sql
--changeset BillyBolton:create-ca-cities-calculate-default-point-trigger-1
CREATE TRIGGER set_default_point
    BEFORE INSERT
    ON ca_cities
    FOR EACH ROW
EXECUTE FUNCTION set_default_point();