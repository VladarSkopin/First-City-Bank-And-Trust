UPDATE clients
SET sub_sector_code = CASE
    WHEN name_or_title IN ('The City Council', 'The Aristocrats League') THEN 'FINL-CAP'
    WHEN name_or_title IN ('The City Pension Fund', 'The City Watch Fund') THEN 'FINL-CFN'
    WHEN name_or_title = 'The Hammerite Order Fund' THEN 'INDU-ENG'
    WHEN name_or_title IN ('Wayside Docks Conglomerate', 'The Caravan Collective') THEN 'INDU-TRN'
    WHEN name_or_title = 'The Silver Vault Exchange' THEN 'FINL-BNK'
    WHEN name_or_title = 'The Eye of the Builder' THEN 'UTIL-MUL'
    WHEN name_or_title = 'The Academy of Precise Sciences' THEN 'HLTH-LST'
    WHEN name_or_title = 'The Alchemical Society' THEN 'MATR-CHE'
    WHEN name_or_title = 'The Void Gazers' THEN 'REAL-OFF'
    ELSE sub_sector_code -- Keep existing value for others
END
WHERE name_or_title IN (
    'The City Council',
    'The City Pension Fund',
    'The City Watch Fund',
    'The Aristocrats League',
    'The Hammerite Order Fund',
    'Wayside Docks Conglomerate',
    'The Caravan Collective',
    'The Silver Vault Exchange',
    'The Eye of the Builder',
    'The Academy of Precise Sciences',
    'The Alchemical Society',
    'The Void Gazers'
);
