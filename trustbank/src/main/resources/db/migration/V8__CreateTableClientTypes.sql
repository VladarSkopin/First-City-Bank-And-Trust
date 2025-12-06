CREATE TABLE if not exists client_types (
    client_type_code TEXT PRIMARY KEY,
    client_type_name TEXT NOT NULL UNIQUE,
    description TEXT
);

COMMENT ON TABLE client_types IS 'Defines the classification categories for banking clients based on their legal entity structure.';
COMMENT ON COLUMN client_types.client_type_code IS 'Unique identifier code for the client type.';
COMMENT ON COLUMN client_types.client_type_name IS 'Human-readable name of the client type.';
COMMENT ON COLUMN client_types.description IS 'Detailed explanation of the client type characteristics and requirements.';
