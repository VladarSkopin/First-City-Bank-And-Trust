CREATE TABLE if not exists vault (
    vault_code TEXT PRIMARY KEY,
    client_code TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    amount BIGINT NOT NULL CHECK (amount >= 0),
    currency_code TEXT NOT NULL,
    is_archived BOOLEAN NOT NULL DEFAULT false,


    -- Foreign key constraints with appropriate referential actions
    CONSTRAINT fk_vault_client
        FOREIGN KEY (client_code)
        REFERENCES clients(client_code)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,  -- Cannot delete client with active vaults

    CONSTRAINT fk_vault_currency
        FOREIGN KEY (currency_code)
        REFERENCES currencies(currency_code)
        ON UPDATE CASCADE
        ON DELETE RESTRICT   -- Cannot delete currency used in active vaults
);

COMMENT ON TABLE vault IS 'This table represents secure storage facilities within the banking institution.';
COMMENT ON COLUMN vault.vault_code IS 'Unique vault storage identifier.';
COMMENT ON COLUMN vault.client_code IS 'Client who owns or rents this vault storage. References the clients table to identify the asset owner.';
COMMENT ON COLUMN vault.created_at IS 'Date and time when the vault storage was initially allocated.';
COMMENT ON COLUMN vault.modified_at IS 'Date and time of the last modification to vault contents or status.';
COMMENT ON COLUMN vault.amount IS 'Total value stored in this vault.';
COMMENT ON COLUMN vault.currency_code IS 'Currency type for the stored amount. References the currencies table to specify the monetary system.';
COMMENT ON COLUMN vault.is_archived IS 'Vault status flag. Archived vaults retain their contents but cannot be modified.';
