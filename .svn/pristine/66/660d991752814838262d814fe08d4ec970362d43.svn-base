use talentpool;

ALTER TABLE tp_positions ADD COLUMN notify_user_ids VARCHAR(100);
ALTER TABLE tp_cr_position_master ADD COLUMN position_date_closed DATETIME NULL AFTER position_date_approved;

-- filters in scheduled reports
ALTER TABLE tp_report_scheduler DROP FOREIGN KEY  tp_report_scheduler_ibfk_4;
ALTER TABLE tp_report_scheduler CHANGE source_id source_ids VARCHAR(255) NULL;
ALTER TABLE tp_report_scheduler ADD COLUMN source_category_ids VARCHAR(255) NULL AFTER source_ids;
ALTER TABLE tp_report_scheduler ADD COLUMN activities VARCHAR(255) NULL AFTER stages;

-- delete applicant
SET FOREIGN_KEY_CHECKS = 0;
ALTER TABLE tp_cr_candidate_master DROP FOREIGN KEY FK_tp_cr_candidate_master;
ALTER TABLE tp_cr_candidate_master ADD CONSTRAINT FK_tp_cr_candidate_master FOREIGN KEY (applicant_id) REFERENCES tp_applicants (applicant_id) ON DELETE CASCADE ;

ALTER TABLE tp_applicant_employment_history DROP FOREIGN KEY tp_applicant_employment_history_ibfk_1;
ALTER TABLE tp_applicant_employment_history ADD CONSTRAINT tp_applicant_employment_history_ibfk_1 FOREIGN KEY (applicant_id) REFERENCES tp_applicants (applicant_id) ON DELETE CASCADE ;

ALTER TABLE tp_todo DROP FOREIGN KEY tp_todo_ibfk_4;
ALTER TABLE tp_todo ADD CONSTRAINT tp_todo_ibfk_4 FOREIGN KEY (appointment_id) REFERENCES tp_appointments (appointment_id) ON DELETE CASCADE ;

ALTER TABLE tp_todo DROP FOREIGN KEY tp_todo_ibfk_2;
ALTER TABLE tp_todo ADD CONSTRAINT tp_todo_ibfk_2 FOREIGN KEY (process_id) REFERENCES tp_applicant_selection_process (process_id) ON DELETE CASCADE ;

ALTER TABLE tp_todo DROP FOREIGN KEY tp_todo_ibfk_1;
ALTER TABLE tp_todo ADD CONSTRAINT tp_todo_ibfk_1 FOREIGN KEY (applicant_id) REFERENCES tp_applicants (applicant_id) ON DELETE CASCADE ;

-- ALTER TABLE tp_cr_event_log ADD CONSTRAINT FK_tp_cr_event_log_process_id FOREIGN KEY (process_id) REFERENCES tp_applicant_selection_process (process_id) ON DELETE CASCADE ;

ALTER TABLE tp_cr_event_log DROP FOREIGN KEY FK_tp_cr_event_log_applicant_id;
ALTER TABLE tp_cr_event_log ADD CONSTRAINT FK_tp_cr_event_log_applicant_id FOREIGN KEY (applicant_id) REFERENCES tp_applicants (applicant_id) ON DELETE CASCADE ;

ALTER TABLE tp_applicant_selection_process DROP FOREIGN KEY tp_applicant_selection_process_ibfk_2;
ALTER TABLE tp_applicant_selection_process ADD CONSTRAINT tp_applicant_selection_process_ibfk_2 FOREIGN KEY (applicant_id) REFERENCES tp_applicants (applicant_id) ON DELETE CASCADE ;

ALTER TABLE tp_applicant_selection_process_temp_traits DROP FOREIGN KEY tp_applicant_selection_process_temp_traits_ibfk_1;
ALTER TABLE tp_applicant_selection_process_temp_traits ADD CONSTRAINT tp_applicant_selection_process_temp_traits_ibfk_1 FOREIGN KEY (applicant_id) REFERENCES tp_applicants (applicant_id) ON DELETE CASCADE ;

ALTER TABLE tp_applicant_selection_process_traits DROP FOREIGN KEY tp_applicant_selection_process_traits_ibfk_1;
ALTER TABLE tp_applicant_selection_process_traits ADD CONSTRAINT tp_applicant_selection_process_traits_ibfk_1 FOREIGN KEY (process_id) REFERENCES tp_applicant_selection_process (process_id) ON DELETE CASCADE ;

SET FOREIGN_KEY_CHECKS = 1;