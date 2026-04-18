CREATE TABLE vault_pools (
    id SERIAL PRIMARY KEY,
    vault_pool_name TEXT NOT NULL UNIQUE,
    is_archived BOOL NOT NULL DEFAULT false,
    currency_code TEXT NOT NULL,
    sector_code TEXT NOT NULL,
    amount_from BIGINT NOT NULL DEFAULT 0 CHECK (amount_from >= 0),
    amount_to BIGINT CHECK (amount_to >= 0),
    created_from TIMESTAMP NOT NULL,
    created_to TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE vault_pools
    ADD CONSTRAINT fk_vault_pools_currency
    FOREIGN KEY (currency_code) REFERENCES currencies(currency_code);

ALTER TABLE vault_pools
    ADD CONSTRAINT fk_vault_pools_sector
    FOREIGN KEY (sector_code) REFERENCES sectors(sector_code);


COMMENT ON TABLE vault_pools IS 'This table represents Vault Pools - a tool for querying and selecting vaults.';
COMMENT ON COLUMN vault_pools.id IS 'Unique identifier for the vault pool (auto-incrementing serial).';
COMMENT ON COLUMN vault_pools.vault_pool_name IS 'Name of the vault pool.';
COMMENT ON COLUMN vault_pools.is_archived IS 'True if the pool is archived.';
COMMENT ON COLUMN vault_pools.currency_code IS 'Currency code, references currencies table.';
COMMENT ON COLUMN vault_pools.sector_code IS 'Sector code, references sectors table.';
COMMENT ON COLUMN vault_pools.amount_from IS 'Minimum amount in the pool (inclusive), must be >= 0, defaults to 0.';
COMMENT ON COLUMN vault_pools.amount_to IS 'Maximum amount in the pool (inclusive), must be >= 0 if provided.';
COMMENT ON COLUMN vault_pools.created_from IS 'Start timestamp of the pool.';
COMMENT ON COLUMN vault_pools.created_to IS 'End timestamp of the pool, defaults to current timestamp.';
