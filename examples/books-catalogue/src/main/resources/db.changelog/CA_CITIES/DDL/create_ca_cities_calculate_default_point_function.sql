--liquibase formatted sql
--changeset BillyBolton:create-ca-cities-calculate-default-point-function-1
CREATE OR REPLACE FUNCTION set_default_point()
    RETURNS TRIGGER AS
'
    BEGIN
        NEW.point = ST_SetSRID(ST_MakePoint(NEW.longitude, NEW.latitude), 4326);
        RETURN NEW;
    END;
' LANGUAGE plpgsql;