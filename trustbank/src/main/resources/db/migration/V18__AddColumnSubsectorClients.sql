ALTER TABLE clients
ADD COLUMN IF NOT EXISTS sub_sector_code TEXT,
ADD CONSTRAINT fk_client_sub_sector
    FOREIGN KEY (sub_sector_code)
    REFERENCES sub_sectors(sub_sector_code)
    ON UPDATE CASCADE
    ON DELETE SET NULL;  -- Preserves client record if industry classification changes

COMMENT ON COLUMN clients.sub_sector_code IS 'Industry sub-sector classification for business segmentation and risk analysis';
