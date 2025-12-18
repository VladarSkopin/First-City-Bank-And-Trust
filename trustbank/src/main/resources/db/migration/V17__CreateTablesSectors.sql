CREATE TABLE if not exists sectors (
    sector_code TEXT PRIMARY KEY,
    sector_name TEXT NOT NULL UNIQUE,
    description TEXT NOT NULL
);

COMMENT ON TABLE sectors IS 'Top-level economic sector classification for industry analysis and client categorization.';
COMMENT ON COLUMN sectors.sector_code IS 'Unique sector identifier code.';
COMMENT ON COLUMN sectors.sector_name IS 'Official sector name for display in interfaces and documentation.';
COMMENT ON COLUMN sectors.description IS 'Detailed economic characteristics, business activities, and industry drivers.';

CREATE TABLE if not exists sub_sectors (
    sub_sector_code TEXT PRIMARY KEY,
    sub_sector_name TEXT NOT NULL UNIQUE,
    description TEXT NOT NULL,
    sector_code TEXT NOT NULL,


    -- Foreign key constraints with appropriate referential actions
    CONSTRAINT fk_sub_sector
        FOREIGN KEY (sector_code)
        REFERENCES sectors(sector_code)
        ON UPDATE CASCADE
        ON DELETE RESTRICT  -- Prevents orphaned sub-sectors if sector is deleted
);

COMMENT ON TABLE sub_sectors IS 'Detailed industry group classification within economic sectors for granular client analysis.';
COMMENT ON COLUMN sub_sectors.sub_sector_code IS 'Unique sub-sector identifier code.';
COMMENT ON COLUMN sub_sectors.sub_sector_name IS 'Official sub-sector name for display and reporting.';
COMMENT ON COLUMN sub_sectors.description IS 'Detailed description of the sub-sector specific characteristics.';
COMMENT ON COLUMN sub_sectors.sector_code IS 'Parent sector reference for hierarchical classification.';
