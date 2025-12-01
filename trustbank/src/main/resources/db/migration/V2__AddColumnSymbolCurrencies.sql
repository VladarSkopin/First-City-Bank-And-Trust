ALTER TABLE currencies
ADD COLUMN if not exists currency_symbol TEXT NOT NULL;
