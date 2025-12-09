ALTER TABLE clients
ADD CONSTRAINT chk_name_not_empty
CHECK (length(trim(name_or_title)) > 0);
