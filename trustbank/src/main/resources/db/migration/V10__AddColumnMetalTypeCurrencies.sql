ALTER TABLE currencies
ADD COLUMN IF NOT EXISTS metal_type VARCHAR(50) DEFAULT 'UNKNOWN';

COMMENT ON COLUMN currencies.metal_type IS 'The type of metal for this currency.';

--UPDATE currencies SET metal_type = 'UNKNOWN' WHERE metal_type IS NULL;
UPDATE currencies
SET metal_type = CASE
    WHEN LOWER(currency_name) = 'gold' THEN 'GOLD'
    WHEN LOWER(currency_name) = 'silver' THEN 'SILVER'
    WHEN LOWER(currency_name) = 'copper' THEN 'COPPER'
    ELSE 'UNKNOWN'
END
WHERE metal_type = 'UNKNOWN';

ALTER TABLE currencies ALTER COLUMN metal_type SET NOT NULL;
