use talentpool;
ALTER TABLE tp_inbox_settings 
ADD COLUMN exchange_server_name VARCHAR (100) NULL COLLATE latin1_swedish_ci AFTER inbox_outgoing_tls_enabled,
ADD COLUMN domain_name VARCHAR (100) NULL COLLATE latin1_swedish_ci AFTER exchange_server_name,
ADD COLUMN exchange_server_version CHAR (1) NULL COLLATE latin1_swedish_ci AFTER domain_name,
ADD COLUMN is_exchange_smtp CHAR (1)  DEFAULT '0' NULL COLLATE latin1_swedish_ci AFTER exchange_server_version;
