CREATE TABLE vault_transactions (
    transaction_id SERIAL PRIMARY KEY,
    vault_code TEXT NOT NULL REFERENCES vault(vault_code),
    operation_type VARCHAR(20) NOT NULL CHECK (operation_type IN ('INSERT', 'WITHDRAW')),
    amount BIGINT NOT NULL CHECK (amount > 0),
    new_balance BIGINT NOT NULL CHECK (new_balance >= 0),
    transaction_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
