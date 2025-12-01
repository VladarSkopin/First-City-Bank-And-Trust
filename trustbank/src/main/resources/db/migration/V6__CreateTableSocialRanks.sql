CREATE TABLE if not exists social_ranks (
    rank_code TEXT PRIMARY KEY,
    rank_name TEXT NOT NULL UNIQUE,
    description TEXT,
    privilege_level TEXT,
    regulations TEXT
);

COMMENT ON TABLE social_ranks IS 'This table defines various social classes and their associated privileges, which determine access to financial services, credit limits, and banking facilities';
COMMENT ON COLUMN social_ranks.rank_code IS 'Unique identifier for the social rank.';
COMMENT ON COLUMN social_ranks.rank_name IS 'Human-readable name of the social rank (e.g., High Nobility, Guild Merchant).';
COMMENT ON COLUMN social_ranks.description IS 'Detailed explanation of the rank societal position and significance.';
COMMENT ON COLUMN social_ranks.privilege_level IS 'Associated privilege level from the PrivilegeLevel enum. Controls transaction limits, loan approval, and investment portfolio fees';
COMMENT ON COLUMN social_ranks.regulations IS 'Specific banking regulations and restrictions applied to this social rank.';
