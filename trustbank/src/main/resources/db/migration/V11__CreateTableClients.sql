CREATE TABLE if not exists clients (
    client_code TEXT PRIMARY KEY,
    name_or_title TEXT NOT NULL UNIQUE,
    client_type_code TEXT NOT NULL DEFAULT 'UNK',
    social_rank_code TEXT NOT NULL,
    district_code TEXT,
    is_blocked BOOLEAN NOT NULL DEFAULT false,


    -- Foreign key constraints

    CONSTRAINT fk_client_type
        FOREIGN KEY (client_type_code)
        REFERENCES client_types(client_type_code)
        ON UPDATE CASCADE,

    CONSTRAINT fk_social_rank
        FOREIGN KEY (social_rank_code)
        REFERENCES social_ranks(rank_code)
        ON UPDATE CASCADE,

    CONSTRAINT fk_district
        FOREIGN KEY (district_code)
        REFERENCES districts(district_code)
        ON UPDATE CASCADE
);

COMMENT ON TABLE clients IS 'This table stores all banking clients with their categorization and location data.';
COMMENT ON COLUMN clients.client_code IS 'Unique client identification code.';
COMMENT ON COLUMN clients.name_or_title IS 'Legal name or business title of the client. Must be unique to prevent confusion in banking operations.';
COMMENT ON COLUMN clients.client_type_code IS 'References client_types table to determine legal entity structure. Determines documentation requirements, regulatory compliance, and service eligibility';
COMMENT ON COLUMN clients.social_rank_code IS 'References social_ranks table to determine privileges and banking limits. Affects transaction limits, loan eligibility, and vault access rights';
COMMENT ON COLUMN clients.district_code IS 'District of residence or business operation. References districts table for geographic classification';
COMMENT ON COLUMN clients.is_blocked IS 'Client status flag. Blocked clients can still view accounts but cannot initiate transactions.';
