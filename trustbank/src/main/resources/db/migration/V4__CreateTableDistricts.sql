CREATE TABLE if not exists districts (
    district_code TEXT PRIMARY KEY,
    district_name TEXT NOT NULL UNIQUE
);

COMMENT ON TABLE districts IS 'This table serves as a geographic reference for locations within the City, mapping district codes to their names.';
COMMENT ON COLUMN districts.district_code IS 'Abbreviated code identifier. Used as the primary key for referencing locations.';
COMMENT ON COLUMN districts.district_name IS 'Full name of the district (e.g., Dayport, Docks, South Quarter). Must be unique to prevent duplicate district entries.';
