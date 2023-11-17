CREATE OR REPLACE FUNCTION calculate_distance_function() RETURNS TRIGGER AS
$$
DECLARE
    source_city_row RECORD;
    target_city_row RECORD;
BEGIN
    IF TG_TABLE_NAME = 'ca_cities' THEN
        IF TG_OP = 'INSERT' OR TG_OP = 'UPDATE' THEN
            FOR source_city_row IN SELECT id, point FROM ca_cities WHERE id = NEW.id
                LOOP
                    FOR target_city_row IN SELECT id, point FROM ca_cities
                        -- WHERE id <> NEW.id  -- You can include this condition if needed
                        LOOP
                            IF NOT EXISTS (SELECT 1
                                           FROM ca_cities_distance
                                           WHERE (source_point = source_city_row.point AND target_point = target_city_row.point)
                                              OR (source_point = target_city_row.point AND target_point = source_city_row.point))
                            THEN
                                INSERT INTO ca_cities_distance
                                    (source_point,
                                     target_point,
                                     km_distance)
                                VALUES
                                    (source_city_row.point,
                                     target_city_row.point,
                                     trunc(CAST(ST_Distance(source_city_row.point, target_city_row.point) AS numeric), 0));
                            ELSE
                                UPDATE ca_cities_distance
                                SET km_distance = trunc(CAST(ST_Distance(source_city_row.point, target_city_row.point) AS numeric), 0)
                                WHERE (source_point = source_city_row.point AND target_point = target_city_row.point)
                                   OR (source_point = target_city_row.point AND target_point = source_city_row.point);
                            END IF;
                        END LOOP;
                END LOOP;
        END IF;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;
