INSERT INTO clients
(client_code, name_or_title, client_type_code, social_rank_code, district_code, is_blocked)
VALUES
('CLT-00000', 'Basso the Boxman', 'INDV', 'C-1', 'D-4', true),  -- test client
('CLT-00001', 'The Baron', 'INDV', 'N-1', 'D-10', false),
('CLT-00002', 'The High Priest', 'INDV', 'N-1', 'D-10', false),
('CLT-00003', 'The City Council', 'GOVT', NULL, 'D-11', false),
('CLT-00004', 'The City Pension Fund', 'GOVT', NULL, 'D-11', false),
('CLT-00005', 'The City Watch Fund', 'GOVT', NULL, 'D-11', false),
('CLT-00006', 'The Aristocrats League', 'CORP', NULL, 'D-10', false),
('CLT-00007', 'The Hammerite Order Fund', 'NPO', NULL, 'D-4', false),
('CLT-00008', 'Wayside Docks Conglomerate', 'CORP', NULL, 'D-2', false),
('CLT-00009', 'The Caravan Collective', 'CORP', NULL, 'D-8', false),
('CLT-00010', 'The Silver Vault Exchange', 'CORP', NULL, 'D-6', false),
('CLT-00011', 'The Eye of the Builder', 'NPO', NULL, 'D-1', false),
('CLT-00012', 'The Academy of Precise Sciences', 'NPO', NULL, 'D-5', false),
('CLT-00013', 'The Alchemical Society', 'PART', NULL, 'D-12', false),
('CLT-00014', 'The Void Gazers', 'NPO', NULL, 'D-3', false);
