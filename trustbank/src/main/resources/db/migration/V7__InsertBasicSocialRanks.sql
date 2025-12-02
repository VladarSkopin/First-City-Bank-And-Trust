INSERT INTO social_ranks
(rank_code, rank_name, description, privilege_level, regulations)
values
('N-1', 'Noble', 'Aristocratic elite with ancient lineage and political influence', 'HIGHEST', 'By order of the City Council, nobles are to be allowed any amount of assets kept in their vaults, and ANY amount of loans.'),
('M-1', 'Merchant', 'Wealthy traders and guild masters controlling commerce', 'HIGH', 'By order of the City Council, merchants are to be allowed any amount of assets kept in their vaults, and AT MOST THE SAME amount as loans.'),
('H-1', 'Hammerite', 'Religious order with architectural and spiritual authority', 'ELEVATED', 'By order of the City Council, hammerites are to be allowed any amount of assets kept in their vaults, and AT MOST HALF of that amount in loans.'),
('C-1', 'Commoner', 'Working class citizens and skilled artisans', 'STANDARD', 'By order of the City Council, commoners are to be allowed any amount of assets kept in their vaults, and AT MOST ONE THIRD of that amount in loans.'),
('F-1', 'Foreigner', 'Outsiders with limited rights and constant surveillance', 'RESTRICTED', 'By order of the City Council, foreigners are to be allowed any amount of assets kept in their vaults, but NO loans.');
