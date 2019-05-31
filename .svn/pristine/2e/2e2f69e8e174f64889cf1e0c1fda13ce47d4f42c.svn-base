use talentpool;

ALTER TABLE tp_custom_reports ADD COLUMN inactive_query_file_path VARCHAR(255) CHARSET latin1 COLLATE latin1_spanish_ci DEFAULT '' NULL after query_file_path;

CREATE TABLE tp_cr_position_hiring_summary (
  process_date date NOT NULL,
  position_id bigint(20) NOT NULL,
  step_id tinyint(4) NOT NULL,
  step_level tinyint(2) NOT NULL,
  backlog smallint(6) default '0',
  received smallint(6) default '0',
  cleared smallint(6) default '0',
  rejected smallint(6) default '0',
  inprocess smallint(6) default '0',
  backlog_ids text,
  received_ids text,
  cleared_ids text,
  rejected_ids text,
  inprocess_ids text,
  date_created datetime NOT NULL,
  PRIMARY KEY  (process_date,position_id,step_id),
  KEY FK_step_id (step_id),
  KEY FK_step_level (step_level),
  KEY FK_position_id (position_id),
  KEY phs_process_date (process_date),
  CONSTRAINT FK_tp_cr_position_hiring_summary_position_id FOREIGN KEY (position_id) REFERENCES tp_positions (position_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_tp_cr_position_hiring_summary_step_id FOREIGN KEY (step_id) REFERENCES tp_step_master (step_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_tp_cr_position_hiring_summary_step_level FOREIGN KEY (step_level) REFERENCES tp_step_level (step_level) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE tp_cr_user_hiring_activity_summary (
  process_date date NOT NULL,
  position_id bigint(20) NOT NULL,
  step_id int(11) NOT NULL,
  step_level tinyint(2) NOT NULL,
  user_id bigint(20) NOT NULL,
  received smallint(6) default '0',
  cleared smallint(6) default '0',
  rejected smallint(6) default '0',
  received_ids text,
  cleared_ids text,
  rejected_ids text,
  date_created datetime NOT NULL,
  PRIMARY KEY  (process_date,position_id,step_id,user_id),
  KEY FK_tp_cr_user_hiring_activity_summary (step_id),
  KEY FK_tp_cr_user_hiring_activity_summary_pos (position_id),
  KEY FK_tp_cr_user_hiring_activity_summary_user (user_id),
  KEY FK_tp_cr_user_hiring_activity_summary_step_level (step_level),
  KEY uhas_process_date (process_date),
  CONSTRAINT FK_tp_cr_user_hiring_activity_summary_pos FOREIGN KEY (position_id) REFERENCES tp_positions (position_id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_tp_cr_user_hiring_activity_summary_step_level FOREIGN KEY (step_level) REFERENCES tp_step_level (step_level) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT FK_tp_cr_user_hiring_activity_summary_user FOREIGN KEY (user_id) REFERENCES tp_users (USER_ID) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Additional indexes to improve Inbox performance
ALTER TABLE tp_applicant_inbox_emails ADD CONSTRAINT FK_tp_applicant_inbox_emails_folder_id FOREIGN KEY (folder_id) REFERENCES tp_inbox_folders (folder_id);

ALTER TABLE tp_inbox_emails ADD CONSTRAINT FK_tp_inbox_emails_folder_id FOREIGN KEY (folder_id) REFERENCES tp_inbox_folders (folder_id);

ALTER TABLE tp_applicants ADD INDEX index_applicant_email2 (applicant_email2);

-- Bulk Feedback
ALTER TABLE tp_applicant_selection_process_temp_traits ADD COLUMN select_field_id VARCHAR(250) NULL after rating_field_id;
ALTER TABLE tp_applicant_selection_process_temp_traits ADD COLUMN feedback_form_id int(11) NULL AFTER select_field_id;

ALTER TABLE tp_applicant_selection_process_temp_traits ADD CONSTRAINT FK_tp_applicant_selection_process_temp_traits_feedback_form_id FOREIGN KEY (feedback_form_id) REFERENCES tp_feedback_forms (feedback_form_id);

-- Undo Feedback
ALTER TABLE tp_cr_event_log DROP FOREIGN KEY FK_tp_cr_event_log_process_id;