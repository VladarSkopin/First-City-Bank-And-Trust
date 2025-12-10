CREATE TABLE if not exists currencies (
    currency_code TEXT PRIMARY KEY,
    currency_name TEXT NOT NULL UNIQUE
);

COMMENT ON TABLE currencies IS 'This table stores currency codes and names.';
COMMENT ON COLUMN currencies.currency_code IS 'This serves as the primary identifier for each currency.';
COMMENT ON COLUMN currencies.currency_name IS 'Official name of the currency. Must be unique to prevent duplicate currency entries.';
