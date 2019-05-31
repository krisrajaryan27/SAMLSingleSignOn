use talentpool;

-- moved the below scripts to 13.0.0 in order to make upgrades from 12.9.9 to 13.0.0 work properly
-- ALTER TABLE tp_audit_entries ADD COLUMN source_ip VARCHAR(50) DEFAULT '' NOT NULL AFTER user_id;