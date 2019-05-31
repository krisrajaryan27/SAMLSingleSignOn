use talentpool;

-- from 12.9.3
CREATE TABLE tp_security_questions ( 
	security_question_id INT(11) NOT NULL AUTO_INCREMENT , 
	security_question VARCHAR(250) NOT NULL , 
	PRIMARY KEY (security_question_id)
);

ALTER TABLE tp_users ADD COLUMN password_date_modified DATETIME NULL AFTER location_id, 
ADD COLUMN force_password_change CHAR(1) DEFAULT '0' NOT NULL AFTER password_date_modified;

ALTER TABLE tp_users ADD COLUMN old_passwords VARCHAR(300) NULL AFTER location_id;

CREATE TABLE tp_user_security_question_answer (
	user_id BIGINT(20) NOT NULL,
	security_question_id INT(11) NOT NULL,
	answer VARCHAR(250) NOT NULL,
	KEY FK_tp_user_security_question_answer_user_id (user_id),
	KEY FK_tp_user_security_question_answer_security_question_id (security_question_id),
	CONSTRAINT FK_tp_user_security_question_answer_security_question_id FOREIGN KEY (security_question_id) 
		REFERENCES tp_security_questions (security_question_id) ON DELETE RESTRICT,
	CONSTRAINT FK_tp_user_security_question_answer_user_id FOREIGN KEY (user_id) 
		REFERENCES tp_users (USER_ID) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE tp_user_uuid_map (
	user_id BIGINT(20) NOT NULL,
	uuid VARCHAR(150) NOT NULL,
	date_created DATETIME NOT NULL,
	KEY FK_tp_user_uuid_map (user_id),
	CONSTRAINT FK_tp_user_uuid_map FOREIGN KEY (user_id) REFERENCES tp_users (USER_ID) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- from 12.9.5
ALTER TABLE tp_audit_entries ADD COLUMN source_ip VARCHAR(50) DEFAULT '' NOT NULL AFTER user_id;

ALTER TABLE tp_positions ADD COLUMN approval_user_ids VARCHAR(100);
