--liquibase formatted sql
--changeset BillyBolton:create-ca-cities-calculate-distance-trigger-1
CREATE OR REPLACE TRIGGER calculate_distance_trigger
    AFTER INSERT OR UPDATE
    ON ca_cities
    FOR EACH ROW
EXECUTE FUNCTION calculate_distance_function();