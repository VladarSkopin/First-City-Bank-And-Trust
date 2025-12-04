INSERT INTO client_types
(client_type_code, client_type_name, description)
values
('INDV', 'Individual', 'Single person banking client. Subject to individual tax reporting requirements.'),
('CORP', 'Corporation', 'Legally incorporated business entity. Requires articles of incorporation, corporate resolution, and business registration documents. Eligible for business banking services and commercial loans.'),
('PART', 'Partnership', 'Business partnership between two or more individuals. Requires partnership agreement, tax ID, and partner authorization documents. Partners have joint liability for accounts.'),
('TRST', 'Trust', 'Legal arrangement where assets are held by one party for the benefit of another. Requires trust agreement, trustee documentation, and beneficiary information.'),
('NPO', 'Non-Profit Organization', 'Charitable or non-profit entity. Requires tax-exempt certification, organizational bylaws, and board resolution. Eligible for special non-profit banking services.'),
('GOVT', 'Government Entity', 'Municipal, state, or federal government organization. Requires government authorization, official seals, and agency documentation. Subject to public fund regulations.'),
('UNK', 'Unknown', 'Unknown client type (by default).');
